package com.demo.task.management.service.impl;

import com.demo.task.management.entity.Task;
import com.demo.task.management.repository.TaskRepository;
import com.demo.task.management.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    @Override
    public Task createTask(Task task) {
        return taskRepository.save(task);
    }
    @Override
    public Task updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getStatus());
            task.setPriority(updatedTask.getPriority());
            task.setDueDate(updatedTask.getDueDate());
            return taskRepository.save(task);
        }).orElseThrow(() -> new RuntimeException("Task not found with id " + id));
    }
    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
