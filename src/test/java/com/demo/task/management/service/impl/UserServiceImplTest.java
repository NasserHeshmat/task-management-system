package com.demo.task.management.service.impl;

import com.demo.task.management.entity.User;
import com.demo.task.management.exception.CustomException;
import com.demo.task.management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.demo.task.management.constant.ErrorMessages.USER_ALREADY_EXISTS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegisterUser_Success() {

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        userService.registerUser(user);

        verify(userRepository, times(1)).existsByUsername("testuser");
        verify(passwordEncoder, times(1)).encode("password");
        verify(userRepository, times(1)).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    void testRegisterUser_UserAlreadyExists() {
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        CustomException exception = assertThrows(CustomException.class, () -> userService.registerUser(user));
        assertEquals(USER_ALREADY_EXISTS, exception.getMessage());
        assertEquals(HttpStatus.CONFLICT, exception.getHttpStatus());
        verify(userRepository, times(1)).existsByUsername("testuser");
        verify(userRepository, never()).save(any(User.class));
    }
}
