package com.example.siternbackend.files.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileDto {
    private Long id;
    private String fileName;
    private String filePath;
    private Date uploadedDate;
    private String status;
    private String comment;
//      // เพิ่มฟิลด์ userId เพื่ออ้างอิงไปยังผู้ใช้
}