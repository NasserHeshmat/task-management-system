package com.demo.task.management.controller;

import com.demo.task.management.entity.User;
import com.demo.task.management.model.ConfirmationResponse;
import com.demo.task.management.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static com.demo.task.management.constant.ErrorMessages.STUDENT_CREATED_SUCCESSFULLY;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ConfirmationResponse> registerUser(@Valid @RequestBody User user) {
        userService.registerUser(user);
        return ResponseEntity.ok(new ConfirmationResponse(STUDENT_CREATED_SUCCESSFULLY));
    }
}
