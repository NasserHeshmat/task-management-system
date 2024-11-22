package com.demo.task.management.service.impl;

import com.demo.task.management.entity.User;
import com.demo.task.management.exception.CustomException;
import com.demo.task.management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.demo.task.management.repository.UserRepository;
import org.springframework.stereotype.Service;

import static com.demo.task.management.constant.ErrorMessages.USER_ALREADY_EXISTS;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    @Override
    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(userRepository.existsByUsername(user.getUsername()))
            throw new CustomException(USER_ALREADY_EXISTS, HttpStatus.CONFLICT);
        userRepository.save(user);
    }
}
