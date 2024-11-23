package com.demo.task.management.service.impl;

import com.demo.task.management.constant.Status;
import com.demo.task.management.entity.Task;
import com.demo.task.management.exception.CustomException;
import com.demo.task.management.model.TaskCreateDto;
import com.demo.task.management.repository.TaskRepository;
import com.demo.task.management.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.demo.task.management.constant.ErrorMessages.NOT_FOUND_TASK;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", Status.TODO, 1, LocalDate.now());
        Task task2 = new Task(2L, "Task 2", "Description 2", Status.IN_PROGRESS, 2, LocalDate.now().plusDays(1));
        Page<Task> tasksPage = new PageImpl<>(Arrays.asList(task1, task2));

        when(taskRepository.findAll(PageRequest.of(0, 10))).thenReturn(tasksPage);

        Page<Task> result = taskService.getAllTasks(PageRequest.of(0, 10));
        assertEquals(2, result.getContent().size());
        verify(taskRepository, times(1)).findAll(PageRequest.of(0, 10));
    }

    @Test
    void testGetTaskById() {
        Task task = new Task(1L, "Task 1", "Description 1", Status.TODO, 1, LocalDate.now());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Optional<Task> result = taskService.getTaskById(1L);
        assertTrue(result.isPresent());
        assertEquals("Task 1", result.get().getTitle());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateTask() {
        TaskCreateDto taskDto = new TaskCreateDto("New Task", "New Description", Status.TODO.name(),3, LocalDate.now());
        Task task = new Task(1L, "New Task", "New Description", Status.TODO, 3, LocalDate.now());

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task result = taskService.createTask(taskDto);
        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTaskSuccess() {
        Task existingTask = new Task(1L, "Existing Task", "Old Description", Status.TODO, 2, LocalDate.now());
        TaskCreateDto updatedTaskDto = new TaskCreateDto("Updated Task", "Updated Description", Status.DONE.name(), 1, LocalDate.now().plusDays(1));

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        Task result = taskService.updateTask(1L, updatedTaskDto);
        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals(Status.DONE, result.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTaskNotFound() {
        TaskCreateDto updatedTaskDto = new TaskCreateDto("Updated Task", "Updated Description", Status.DONE.name(), 1, LocalDate.now().plusDays(1));

        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        CustomException exception = assertThrows(CustomException.class, () -> taskService.updateTask(1L, updatedTaskDto));
        assertEquals(NOT_FOUND_TASK + 1, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(0)).save(any(Task.class));
    }

    @Test
    void testDeleteTaskSuccess() {
        doNothing().when(taskRepository).deleteById(1L);

        assertDoesNotThrow(() -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteTaskNotFound() {
        doThrow(new EmptyResultDataAccessException(1)).when(taskRepository).deleteById(1L);

        CustomException exception = assertThrows(CustomException.class, () -> taskService.deleteTask(1L));
        assertEquals(NOT_FOUND_TASK + 1, exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSearchTasks() {
        Task task1 = new Task(1L, "Task 1", "Description 1", Status.TODO, 1, LocalDate.now());
        Task task2 = new Task(2L, "Task 2", "Description 2", Status.IN_PROGRESS, 2, LocalDate.now().plusDays(1));

        when(taskRepository.searchTasks("Task", null, null, null)).thenReturn(Arrays.asList(task1, task2));

        List<Task> result = taskService.searchTasks(new Task(null, "Task", null, null, null, null));
        assertEquals(2, result.size());
        verify(taskRepository, times(1)).searchTasks("Task", null, null, null);
    }
}
