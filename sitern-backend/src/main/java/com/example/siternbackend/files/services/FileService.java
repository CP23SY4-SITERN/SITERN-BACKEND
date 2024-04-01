package com.example.siternbackend.files.services;

import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.files.repositories.FileRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
}
