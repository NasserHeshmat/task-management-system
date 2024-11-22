package com.demo.task.management.controller;

import com.demo.task.management.entity.Task;
import com.demo.task.management.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @GetMapping
    @RolesAllowed("ROLE_ADMIN")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) {
        try {
            return ResponseEntity.ok(taskService.updateTask(id, task));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}