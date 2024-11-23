package com.demo.task.management.scheduler;

import com.demo.task.management.entity.Task;
import com.demo.task.management.repository.TaskRepository;
import com.demo.task.management.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.demo.task.management.constant.Constants.EMAIL_BODY;
import static com.demo.task.management.constant.Constants.EMAIL_SUBJECT;

@Component
@RequiredArgsConstructor
public class TaskNotificationScheduler {

    private final TaskRepository taskRepository;
    private final EmailService emailService;

    @Value("${task.notification.email}")
    private String notificationEmail;

    @Scheduled(cron = "${task.notification.cron}")
    public void sendUpcomingTaskNotifications() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        List<Task> tasksDueTomorrow = taskRepository.findByDueDate(tomorrow);

        for (Task task : tasksDueTomorrow) {
            String emailBody = String.format(EMAIL_BODY, task.getTitle(), task.getDueDate());
            emailService.sendEmail(
                    notificationEmail,
                    EMAIL_SUBJECT,
                    emailBody
            );
        }
    }
}

