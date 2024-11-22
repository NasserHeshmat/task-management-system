package com.demo.task.management.service;

import com.demo.task.management.entity.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> getAllTasks();

    Optional<Task> getTaskById(Long id);

    Task createTask(Task task);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);
}
