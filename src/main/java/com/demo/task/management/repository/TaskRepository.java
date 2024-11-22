package com.demo.task.management.repository;

import com.demo.task.management.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDueDate(LocalDate dueDate);

}