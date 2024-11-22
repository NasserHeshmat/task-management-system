package com.demo.task.management.service;

import com.demo.task.management.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Page<Task> getAllTasks(Pageable pageable);

    Optional<Task> getTaskById(Long id);

    Task createTask(Task task);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);

    List<Task> searchTasks(Task task);
}
