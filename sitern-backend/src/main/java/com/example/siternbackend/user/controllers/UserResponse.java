package com.example.siternbackend.user.controllers;

import com.example.siternbackend.files.entities.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer id;
    private String username;
    private String email;
    private List<String> authorities;
    private List<File> files;

}
