package com.demo.task.management.repository;

import com.demo.task.management.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Course getReferenceById(Long courseId);
}
