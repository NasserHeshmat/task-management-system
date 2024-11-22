package com.demo.task.management.entity;

import com.demo.task.management.constant.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TASK")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Integer priority;
    private LocalDate dueDate;
}

