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

    @NotBlank(message = BLANK_USERNAME)
    private String username;
    @NotBlank(message = PASSWORD_BLANK)
    private String password;

}

