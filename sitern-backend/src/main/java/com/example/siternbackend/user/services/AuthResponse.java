package com.example.siternbackend.user.services;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private boolean isSuccess;
    private String accessToken;
//    private DecodedToken decodedToken;
    private String message;
    private String errorMessage;

    public AuthResponse(boolean isSuccess, String accessToken, String message) {
        this.isSuccess = isSuccess;
        this.accessToken = accessToken;
        this.message = message;
    }


    public AuthResponse(boolean isSuccess, String errorMessage) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
    }



    // Getters and setters
}