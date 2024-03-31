package com.example.siternbackend.files.controllers;

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
}
