package com.example.siternbackend.files.controllers;

import jakarta.persistence.Entity;
import lombok.*;

@Getter
@Setter
public class UpdateFileRequest {
    private String status;
    private String comments;
    private String reason;
}