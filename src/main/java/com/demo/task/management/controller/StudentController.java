package com.demo.task.management.controller;

import com.demo.task.management.entity.Student;
import com.demo.task.management.model.ConfirmationResponse;
import com.demo.task.management.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import static com.demo.task.management.constant.ErrorMessages.STUDENT_CREATED_SUCCESSFULLY;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/register")
    public ResponseEntity<ConfirmationResponse> registerUser(@Valid @RequestBody Student student) {
        studentService.registerStudent(student);
        return ResponseEntity.ok(new ConfirmationResponse(STUDENT_CREATED_SUCCESSFULLY));
    }
}
