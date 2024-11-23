package com.demo.task.management.model;

import com.demo.task.management.constant.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

import static com.demo.task.management.constant.ErrorMessages.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateDto {

    @NotBlank(message = BLANK_TITLE)
    private String title;
    private String description;
    @Pattern(regexp = "TODO|IN_PROGRESS|DONE", message = INVALID_STATUS)
    private String status;
    private Integer priority;
    @NotNull(message = BLANK_DUE_DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    public Status getEnumStatus() {
        return Status.valueOf(status);
    }
}
