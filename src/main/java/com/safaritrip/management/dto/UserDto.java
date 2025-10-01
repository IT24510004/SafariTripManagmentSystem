package com.safaritrip.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {

    @NotEmpty(message = "Username should not be empty")
    private String username;

    @NotEmpty(message = "Password should not be empty")
    private String password;

    @NotEmpty(message = "Please confirm your password")
    private String confirmPassword;

    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;

    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;
}