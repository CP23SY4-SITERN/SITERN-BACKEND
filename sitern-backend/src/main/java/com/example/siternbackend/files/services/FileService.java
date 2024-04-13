package com.example.siternbackend.files.services;

import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.files.entities.FileDto;
import com.example.siternbackend.files.repositories.FileRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {
    private final FileRepositories fileRepository;

    @Autowired
    public FileService(FileRepositories fileRepository) {
        this.fileRepository = fileRepository;
    }
    public List<File> getAllFiles() {
        return fileRepository.findAll();
    }

    public List<FileDto> getFilesByUserId(Integer userId) {
        // ดึงข้อมูลไฟล์ของผู้ใช้ตาม userId โดยใช้ FileRepositories
        List<File> files = fileRepository.findAllByUserId(userId);

        // แปลง Entity File เป็น FileDto
        return files.stream()
                .map(this::convertToFileDto)
                .collect(Collectors.toList());
    }

    // เมธอดสำหรับแปลง Entity File เป็น FileDto
    private FileDto convertToFileDto(File file) {
        return FileDto.builder()
                .id(file.getId())
                .fileName(file.getFileName())
                .filePath(file.getFilePath())
                .uploadedDate(file.getUploadedDate())
                .status(file.getStatus())
                .build();
    }
}
