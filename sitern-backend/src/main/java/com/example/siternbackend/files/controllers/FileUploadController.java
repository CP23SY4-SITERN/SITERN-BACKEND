package com.example.siternbackend.files.controllers;

import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.files.services.FileStorageService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public FileUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

//    @PostMapping("/upload")
//    public ResponseEntity<UploadResponse> uploadFile(
//            @RequestParam(name = "file", required = false) MultipartFile file
////            @RequestParam("fullName") String fullName,
////            @RequestParam("dateOfBirth") String dateOfBirth
//    ) {
//        String fileName = fileStorageService.storeFile(file);
//
//        UploadResponse uploadResponse = new UploadResponse(fileName);
//
//        return ResponseEntity.ok().body(uploadResponse);
//    }
    @PostMapping("/upload")
    public ResponseEntity<UploadResponse> uploadFile(
        @RequestParam("file") MultipartFile file
    ) {
        if (file == null || file.isEmpty()) {
        return ResponseEntity.badRequest().build();
        }

        String fileName = fileStorageService.storeFile(file);

     if (fileName == null) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


    // You can add more details to the response, like download URL or file metadata
    UploadResponse uploadResponse = new UploadResponse(fileName);

    return ResponseEntity.ok(uploadResponse);
    }


    @PostMapping("/upload/tr-document")
    public ResponseEntity<UploadResponse> uploadTrDocument(
            @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String fileName = fileStorageService.storeTrDocument(file);

        if (fileName == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // Set uploadedDate to current timestamp
        Date uploadedDate = new Date();

        // Set status to "waiting for approve"
        String status = "waiting for approve";
        // Save the file information to the database
        // Create a new File object and set its properties
        File uploadedFile = new File();
        uploadedFile.setFileName(fileName);
        uploadedFile.setFilePath("/api/files/tr-document/" + fileName);
        uploadedFile.setUploadedDate(uploadedDate);
        uploadedFile.setStatus(status);
        fileStorageService.saveFile(uploadedFile);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        return ResponseEntity.ok(uploadResponse);
    }
    @PostMapping("/upload/tr-document/TR01")
    public ResponseEntity<UploadResponse> uploadTr01Document(
            @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String fileName = fileStorageService.storeTr01Document(file);

        if (fileName == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // Set uploadedDate to current timestamp
        Date uploadedDate = new Date();

        // Set status to "waiting for approve"
        String status = "waiting for approve";
        // Save the file information to the database
        // Create a new File object and set its properties
        File uploadedFile = new File();
        uploadedFile.setFileName(fileName);
        uploadedFile.setFilePath("/api/files/tr-document/TR01/" + fileName);
        uploadedFile.setUploadedDate(uploadedDate);
        uploadedFile.setStatus(status);
        fileStorageService.saveFile(uploadedFile);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        return ResponseEntity.ok(uploadResponse);
    }
    @PostMapping("/upload/tr-document/TR02")
    public ResponseEntity<UploadResponse> uploadTr02Document(
            @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String fileName = fileStorageService.storeTr02Document(file);

        if (fileName == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // Set uploadedDate to current timestamp
        Date uploadedDate = new Date();

        // Set status to "waiting for approve"
        String status = "waiting for approve";
        // Save the file information to the database
        // Create a new File object and set its properties
        File uploadedFile = new File();
        uploadedFile.setFileName(fileName);
        uploadedFile.setFilePath("/api/files/tr-document/TR02/" + fileName);
        uploadedFile.setUploadedDate(uploadedDate);
        uploadedFile.setStatus(status);
        fileStorageService.saveFile(uploadedFile);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        return ResponseEntity.ok(uploadResponse);
    }
    @PostMapping("/upload/resume")
    public ResponseEntity<UploadResponse> uploadResume(
            @RequestParam("file") MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        String fileName = fileStorageService.storeResume(file);

        if (fileName == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        // Set uploadedDate to current timestamp
        Date uploadedDate = new Date();

        // Set status to "waiting for approve"
        String status = "waiting for approve";
        // Save the file information to the database
        // Create a new File object and set its properties
        File uploadedFile = new File();
        uploadedFile.setFileName(fileName);
        uploadedFile.setFilePath("/api/files/resume/" + fileName);
        uploadedFile.setUploadedDate(uploadedDate);
        uploadedFile.setStatus(status);
        fileStorageService.saveFile(uploadedFile);
        UploadResponse uploadResponse = new UploadResponse(fileName);
        return ResponseEntity.ok(uploadResponse);
    }
    @GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        // Try to determine file's content type
        String contentType;
        try {
            contentType = Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
        } catch (IOException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        // Fallback to octet-stream if content type could not be determined
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/{directory}/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String directory, @PathVariable String fileName) {
        // Load file as Resource based on the directory
        Resource resource;
        if ("TR_Document".equals(directory)) {
            resource = fileStorageService.loadTrDocumentAsResource(fileName);
        } else if ("resume".equals(directory)) {
            resource = fileStorageService.loadResumeAsResource(fileName);
        } else {
            // Invalid directory
            return ResponseEntity.notFound().build();
        }

        if (resource == null) {
            return ResponseEntity.notFound().build();
        }

        // Try to determine file's content type
        String contentType;
        try {
            contentType = Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
        } catch (IOException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        // Fallback to octet-stream if content type could not be determined
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
