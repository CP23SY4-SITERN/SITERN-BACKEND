package com.example.siternbackend.files.services;

import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.files.repositories.FileRepositories;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final Path trDocumentStorageLocation;
    private final Path resumeStorageLocation;
    private final FileRepositories fileRepositories;
    @Autowired
    public FileStorageService(Environment env, FileRepositories fileRepositories) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();
        this.trDocumentStorageLocation = Paths.get(env.getProperty("app.file.tr-document-dir", "./files/TR_Document"))
                .toAbsolutePath().normalize();
        this.resumeStorageLocation = Paths.get(env.getProperty("app.file.resume-dir", "./files/resume"))
                .toAbsolutePath().normalize();
        this.fileRepositories = fileRepositories;
        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.trDocumentStorageLocation);
            Files.createDirectories(this.resumeStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException(
                    "Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        String[] fileNameParts = fileName.split("\\.");

        return fileNameParts[fileNameParts.length - 1];
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
        String fileName =
                new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public String storeTrDocument(MultipartFile file) {
        // Normalize file name
        String fileName =
                new Date().getTime() + "-TR-File." + getFileExtension(file.getOriginalFilename());

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.trDocumentStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeResume(MultipartFile file) {
        // Normalize file name
        String fileName =
                new Date().getTime() + "-Resume." + getFileExtension(file.getOriginalFilename());

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.trDocumentStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Malformed URL exception occurred while loading file: " + fileName, ex);
        }
    }
    public Resource loadTrDocumentAsResource(String fileName) {
        try {
            Path filePath = this.trDocumentStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Malformed URL exception occurred while loading file: " + fileName, ex);
        }
    }

    public Resource loadResumeAsResource(String fileName) {
        try {
            Path filePath = this.resumeStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Malformed URL exception occurred while loading file: " + fileName, ex);
        }
    }
    @Transactional
    public void saveFile(File file) {
        // ทำการบันทึกข้อมูลไฟล์ลงในฐานข้อมูลโดยใช้ repository
        if (file.getFilePath() == null) {
            throw new IllegalArgumentException("File path must not be null");
        }
        fileRepositories.save(file);
    }
}
