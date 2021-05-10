package com.cp.contactpad.payload;

import com.cp.contactpad.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class SignUpRequest implements Serializable {
    private String name;
    @NotBlank(message = "FirstName must not be empty")
    @JsonProperty
    private String firstName;
    @NotBlank(message = "LastName must not be empty")
    private String lastName;

    @NotBlank(message = "Email must not be empty")
    @Email
    @JsonProperty
    private String email;

    @NotBlank(message = "Please enter a phone number")
    @JsonProperty
    private String mobile_phone;

    @NotBlank(message = "Please select a gender")
    @JsonProperty
    private String gender;

    @NotBlank(message = "Please enter a password")
    @Size(min = 8, max = 100, message = "Password length must 8 and above")
    @ValidPassword
    @JsonProperty
    private String password;

}
