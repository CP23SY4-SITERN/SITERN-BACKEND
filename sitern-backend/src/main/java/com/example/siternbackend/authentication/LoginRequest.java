package com.example.siternbackend.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {
        @NotEmpty(message = "Username cannot be empty")
        String username;
        @NotEmpty(message = "Password cannot be empty")
        String password;
}