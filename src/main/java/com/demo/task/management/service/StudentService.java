package com.demo.task.management.service;

import com.demo.task.management.entity.Student;

import javax.validation.Valid;

public interface StudentService {
    void registerStudent(@Valid Student student);
}
