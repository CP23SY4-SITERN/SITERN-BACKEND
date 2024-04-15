package com.example.siternbackend.files.controllers;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileResponse {
    private Long id;
    private String fileName;
    private String filePath;
    private Date uploadedDate;
    private String status;
//    private Integer user_id;
    private String stdName;

}

