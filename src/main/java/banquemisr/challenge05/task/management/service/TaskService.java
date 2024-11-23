package banquemisr.challenge05.task.management.service;

import banquemisr.challenge05.task.management.entity.Task;
import banquemisr.challenge05.task.management.model.TaskCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Page<Task> getAllTasks(Pageable pageable);

    Optional<Task> getTaskById(Long id);

    Task createTask(TaskCreateDto task);

    Task updateTask(Long id, TaskCreateDto updatedTask);

    void deleteTask(Long id);

    List<Task> searchTasks(Task task);
}
