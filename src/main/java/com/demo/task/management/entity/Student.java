package com.demo.task.management.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.demo.task.management.constant.ErrorMessages.*;

@Entity
@Data
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = BLANK_FIRSTNAME)
    private String firstName;
    @NotBlank(message = BLANK_LASTNAME)
    private String lastName;

    @Email(message = INVALID_EMAIL)
    @NotBlank(message = EMAIL_BLANK)
    @Column(unique = true)
    private String email;

    @NotBlank(message = BLANK_PASSWORD)
    private String password;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "registration",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> enrolledCourses;
}

