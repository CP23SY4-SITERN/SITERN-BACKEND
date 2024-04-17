package com.example.siternbackend.files.services;

import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.files.repositories.FileRepositories;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.services.DecodedTokenService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.Date;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;
    private final Path trDocumentStorageLocation;
    private final Path tr01DocumentStorageLocation;
    private final Path tr02DocumentStorageLocation;
    private final Path resumeStorageLocation;
    private final Path profilePictureLocation;
    private final FileRepositories fileRepositories;
    private final DecodedTokenService decodedTokenService;

    @Autowired
    public FileStorageService(Environment env, FileRepositories fileRepositories, DecodedTokenService decodedTokenService) {
        this.fileStorageLocation = Paths.get(env.getProperty("app.file.upload-dir", "./uploads/files"))
                .toAbsolutePath().normalize();
        this.trDocumentStorageLocation = Paths.get(env.getProperty("app.file.tr-document-dir", "./files/TR_Document"))
                .toAbsolutePath().normalize();
        this.tr01DocumentStorageLocation = Paths.get(env.getProperty("app.file.tr01-document-dir", ".././files/tr_document/TR-01"))
                .toAbsolutePath().normalize();
        this.tr02DocumentStorageLocation = Paths.get(env.getProperty("app.file.tr02-document-dir", "./files/TR_Document/TR02"))
                .toAbsolutePath().normalize();
        this.resumeStorageLocation = Paths.get(env.getProperty("app.file.resume-dir", "./files/resume"))
                .toAbsolutePath().normalize();
        this.profilePictureLocation = Paths.get(env.getProperty("app.file.resume-dir", "./files/profile-picture"))
                .toAbsolutePath().normalize();
        this.fileRepositories = fileRepositories;
        this.decodedTokenService = decodedTokenService;
        try {
            Files.createDirectories(this.fileStorageLocation);
            Files.createDirectories(this.trDocumentStorageLocation);
            Files.createDirectories(this.resumeStorageLocation);
            Files.createDirectories(this.tr01DocumentStorageLocation);
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

    public String storeTr01Document(String username, MultipartFile file) {
        // Normalize file name


        String fileName =
                username + "-TR01-File." + getFileExtension(file.getOriginalFilename());

        try {
            // Check if the filename contains invalid characters
            if (fileName.contains("..")) {
                throw new RuntimeException(
                        "Sorry! Filename contains invalid path sequence " + fileName);
            }

            Path targetLocation = this.tr01DocumentStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public String storeTr02Document(MultipartFile file) {
        // Normalize file name
        String fileName =
                new Date().getTime() + "-TR01-File." + getFileExtension(file.getOriginalFilename());

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

    public String storeProfilePicture(String username, MultipartFile file) {
        // Normalize file name


        String fileName =
                username + "-Profile-Picture." + getFileExtension(file.getOriginalFilename());

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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + fileName);
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
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Malformed URL exception occurred while loading file: " + fileName, ex);
        }
    }

    public Resource loadTr01DocumentAsResource(String fileName) {
        try {
            Path filePath = this.tr01DocumentStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Malformed URL exception occurred while loading file: " + fileName, ex);
        }
    }

    public Resource loadTr02DocumentAsResource(String fileName) {
        try {
            Path filePath = this.tr02DocumentStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new FileSystemNotFoundException("File not found: " + fileName);
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

    public Resource loadProfilePictureAsResource(String fileName) {
        try {
            Path filePath = this.profilePictureLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("Profile-Picture not found: " + fileName);
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

    public boolean deleteFile(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            if (!Files.exists(filePath)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + fileName);
            }
            Files.deleteIfExists(filePath);
            return true;
        } catch (IOException e) {
            // Handle the exception, log or return false based on your requirements
            return false;
        }
    }
}
