package com.example.siternbackend.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
    private String accessToken;

    // Constructor
    public LogoutRequest(String accessToken) {
        this.accessToken = accessToken;
    }
}
