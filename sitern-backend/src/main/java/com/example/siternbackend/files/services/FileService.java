package com.example.siternbackend.files.services;

import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.files.repositories.FileRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    @Autowired
    private FileRepositories fileRepositories;

    public File saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String filePath = "your_upload_directory/" + fileName; // Set your upload directory

        // Save file to server
        byte[] bytes = file.getBytes();
        Path path = Paths.get(filePath);
        Files.write(path, bytes);

        // Save file info to database
        File fileEntity = new File();
        fileEntity.setFileName(fileName);
        fileEntity.setFilePath(filePath);
        return fileRepositories.save(fileEntity);
    }
}
