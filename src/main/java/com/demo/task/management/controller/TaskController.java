package com.demo.task.management.controller;

import com.demo.task.management.entity.Task;
import com.demo.task.management.model.ConfirmationResponse;
import com.demo.task.management.model.TaskCreateDto;
import com.demo.task.management.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;

import static com.demo.task.management.constant.ErrorMessages.TASK_DELETED;

@RestController
@RequestMapping("/api/v1/tasks")
@AllArgsConstructor
public class TaskController {

    private TaskService taskService;

    @GetMapping
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskService.getAllTasks(pageable);
    }

    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @RolesAllowed("ROLE_ADMIN")
    @PostMapping
    public Task createTask(@RequestBody @Valid TaskCreateDto task) {
        return taskService.createTask(task);
    }
    @RolesAllowed("ROLE_ADMIN")
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody @Valid TaskCreateDto task) {
        return ResponseEntity.ok(taskService.updateTask(id, task));
    }
    @RolesAllowed("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<ConfirmationResponse> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok(new ConfirmationResponse(TASK_DELETED));
    }
    @RolesAllowed({"ROLE_ADMIN", "ROLE_USER"})
    @PostMapping("/search")
    public List<Task> searchTasks(@RequestBody Task task) {
        return taskService.searchTasks(task);
    }
}