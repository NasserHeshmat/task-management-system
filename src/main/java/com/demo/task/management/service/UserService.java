package com.demo.task.management.service;

import com.demo.task.management.entity.User;

import javax.validation.Valid;

public interface UserService {
    void registerUser(@Valid User user);
}
