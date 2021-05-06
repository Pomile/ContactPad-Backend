package com.cp.contactpad.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "Please enter an email")
    @Email
    private String email;

    @NotBlank(message = "Please enter a password")
    private String password;
}
