package com.demo.task.management.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.demo.task.management.constant.ErrorMessages.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @Email(message = INVALID_EMAIL)
    @NotBlank(message = EMAIL_BLANK)
    private String email;
    @NotBlank(message = PASSWORD_BLANK)
    private String password;

}

