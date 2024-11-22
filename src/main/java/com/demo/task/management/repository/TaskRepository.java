package com.demo.task.management.repository;

import com.demo.task.management.constant.Status;
import com.demo.task.management.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByDueDate(LocalDate dueDate);

    Page<Task> findAll(Pageable pageable);

    @Query("SELECT t FROM Task t WHERE " +
            "(:title IS NULL OR t.title LIKE %:title%) AND " +
            "(:description IS NULL OR t.description LIKE %:description%) AND " +
            "(:status IS NULL OR t.status = :status) AND " +
            "(:dueDate IS NULL OR t.dueDate = :dueDate)")
    List<Task> searchTasks(@Param("title") String title,
                           @Param("description") String description,
                           @Param("status") Status status,
                           @Param("dueDate") LocalDate dueDate);

}