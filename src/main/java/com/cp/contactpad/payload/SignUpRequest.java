package com.cp.contactpad.payload;

import com.cp.contactpad.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    private String name;
    @NotBlank(message = "FirstName must not be empty")
    private String firstName;
    @NotBlank(message = "LastName must not be empty")
    private String lastName;

    @NotBlank(message = "Email must not be empty")
    @Email
    private String email;

    @NotBlank(message = "Please enter a phone number")
    private String mobile_phone;

    @NotBlank(message = "Please select a gender")
    private String gender;

    @NotBlank(message = "Please enter a password")
    @Size(min = 8, max = 100, message = "Password length must 8 and above")
    @ValidPassword
    private String password;

}
