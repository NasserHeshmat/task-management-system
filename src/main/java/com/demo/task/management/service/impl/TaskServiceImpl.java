package com.demo.task.management.service.impl;

import com.demo.task.management.entity.Task;
import com.demo.task.management.exception.CustomException;
import com.demo.task.management.model.TaskCreateDto;
import com.demo.task.management.repository.TaskRepository;
import com.demo.task.management.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.demo.task.management.constant.ErrorMessages.NOT_FOUND_TASK;

@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

    private TaskRepository taskRepository;

    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }
    @Override
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    @Override
    public Task createTask(TaskCreateDto task) {
        return taskRepository.save(Task.builder()
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .dueDate(task.getDueDate())
                        .status(task.getEnumStatus())
                        .priority(task.getPriority())
                .build());
    }
    @Override
    public Task updateTask(Long id, TaskCreateDto updatedTask) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(updatedTask.getTitle());
            task.setDescription(updatedTask.getDescription());
            task.setStatus(updatedTask.getEnumStatus());
            task.setPriority(updatedTask.getPriority());
            task.setDueDate(updatedTask.getDueDate());
            return taskRepository.save(task);
        }).orElseThrow(() -> new CustomException(NOT_FOUND_TASK + id, HttpStatus.NOT_FOUND));
    }
    @Override
    public void deleteTask(Long id) {
        try {
            taskRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new CustomException(NOT_FOUND_TASK + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Task> searchTasks(Task task) {
        return taskRepository.searchTasks(task.getTitle(),task.getDescription(),task.getStatus(),task.getDueDate());
    }
}
