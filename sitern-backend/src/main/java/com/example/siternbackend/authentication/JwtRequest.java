package com.example.siternbackend.authentication;

import jakarta.validation.constraints.NotEmpty;

public record JwtRequest(@NotEmpty(message = "Token cannot be empty") String token) { }
