package com.demo.task.management.service;

import com.demo.task.management.entity.Course;

import java.util.List;

public interface CourseService {
    void registerToCourse(Long courseId);

    List<Course> findAll();

    byte[] getCourseScheduleAsPdf(Long courseId);

    void cancelCourseRegisteration(Long courseId);
}
