package com.demo.task.management.util;

import com.demo.task.management.entity.Task;
import com.demo.task.management.repository.TaskRepository;
import com.demo.task.management.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class TaskNotificationScheduler {

    private final TaskRepository taskRepository;
    private final EmailService emailService;

    @Scheduled(cron = "0 0 9 * * ?")
    public void sendUpcomingTaskNotifications() {
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);

        List<Task> tasksDueTomorrow = taskRepository.findByDueDate(tomorrow);

        for (Task task : tasksDueTomorrow) {
            String emailBody = String.format("Reminder: Task '%s' is due on %s.", task.getTitle(), task.getDueDate());
            emailService.sendEmail(
                    "nasserheshmat96@gmail.com",
                    "Upcoming Task Deadline",
                    emailBody
            );
        }
    }
}

