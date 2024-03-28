package com.example.siternbackend.user.services;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecodedToken {
    private String name;
    private String preferredUsername;
    private String groupId;
    private String email;

    // Getters and setters
}
