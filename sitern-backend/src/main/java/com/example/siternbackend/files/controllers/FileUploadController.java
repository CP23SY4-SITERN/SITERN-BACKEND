package com.example.siternbackend.files.controllers;

import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.files.repositories.FileRepositories;
import com.example.siternbackend.files.services.FileStorageService;

import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.services.DecodedTokenService;
import com.example.siternbackend.user.services.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final FileStorageService fileStorageService;
    private final DecodedTokenService decodedTokenService;
    private final UserService userService;
    private final FileRepositories fileRepositories;
    @Autowired
    public FileUploadController(FileStorageService fileStorageService, DecodedTokenService decodedTokenService, UserService userService,FileRepositories fileRepositories) {
        this.fileStorageService = fileStorageService;
        this.decodedTokenService = decodedTokenService;
        this.userService = userService;
        this.fileRepositories = fileRepositories;
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
    public ResponseEntity<?> uploadTr01Document(
            @RequestHeader("Authorization") String bearerToken,
            @RequestParam("file") MultipartFile file) throws JSONException {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is empty");
            }
            User user = decodedTokenService.getUserFromToken(bearerToken);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
            }

            // Fetch existing file associated with the user and TR01 document
            File existingFile = fileStorageService.getTr01File(user.getUsername());

            // Store the new file
            String fileName = fileStorageService.storeTr01Document(user.getUsername(), file);
            if (fileName == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to store file");
            }

            // Update the existing file record with the new file information
            if (existingFile != null) {
                // Replace the file content
                String filePath = existingFile.getFilePath();
                fileStorageService.replaceFile(filePath, file.getInputStream());

                // Update file metadata
                existingFile.setFileName(fileName);
                existingFile.setUploadedDate(new Date());
                existingFile.setStatus("waiting for approve");
                existingFile.setComments("");
                fileStorageService.saveFile(existingFile);
            } else {
                // Create a new file record if it doesn't exist
                File uploadedFile = new File();
                uploadedFile.setFileName(fileName);
                uploadedFile.setFilePath("/api/files/tr-document/TR-01/" + fileName);
                uploadedFile.setUploadedDate(new Date());
                uploadedFile.setStatus("waiting for approve");
                uploadedFile.setUser(user);
                uploadedFile.setComments("");
                fileStorageService.saveFile(uploadedFile);
            }

            // Update user status
            user.setStatusTR01("inprogress");
            userService.saveUser(user);

            UploadResponse uploadResponse = new UploadResponse(fileName);
            return ResponseEntity.ok(uploadResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
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
        String comment = "";
        // Save the file information to the database
        // Create a new File object and set its properties
        File uploadedFile = new File();
        uploadedFile.setFileName(fileName);
        uploadedFile.setFilePath("/api/files/tr-document/TR02/" + fileName);
        uploadedFile.setUploadedDate(uploadedDate);
        uploadedFile.setStatus(status);
        uploadedFile.setComments(comment);
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
        } else if ("Profile-Picture".equals(directory)) {
            resource = fileStorageService.loadProfilePictureAsResource(fileName);
        }    else if ("tr-document".equals(directory)) {
                resource = fileStorageService.loadTr01DocumentAsResource(fileName);
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

    @GetMapping("/{directory}/{tr01}/{fileName:.+}")
    public ResponseEntity<?> GetFile(@PathVariable String directory,@PathVariable String tr01, @PathVariable String fileName) {
        // Load file as Resource based on the directory
        Resource resource;
        if ("TR_Document".equals(directory)) {
            resource = fileStorageService.loadTrDocumentAsResource(fileName);
        } else if ("tr-document".equals(directory)) {
                resource = fileStorageService.loadTr01DocumentAsResource(fileName);
        } else {
            // Invalid directory
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Directory");
        }
        if ("TR_Document".equals(tr01)) {
            resource = fileStorageService.loadTr01DocumentAsResource(fileName);
        }   else if ("TR-01".equals(tr01)) {
            resource = fileStorageService.loadTr01DocumentAsResource(fileName);
        } else {
            // Invalid directory
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Directory");
        }
        if (resource == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }


        // Try to determine file's content type
        String contentType;
//        try {
//            contentType = Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
//        } catch (IOException e) {
//            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
//
//        // Fallback to octet-stream if content type could not be determined
//        if (contentType == null) {
//            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
        try {
            if (resource.exists()) {
                contentType = Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found" + fileName);
            }
        } catch (FileNotFoundException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        } catch (IOException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/{SITERN-BACKEND}/{directory}/{tr01}/{fileName:.+}")
    public ResponseEntity<?> GetFileWSitern(@PathVariable String directory,@PathVariable String tr01, @PathVariable String fileName) {
        // Load file as Resource based on the directory
        Resource resource;
        if ("TR_Document".equals(directory)) {
            resource = fileStorageService.loadTrDocumentAsResource(fileName);
        } else if ("tr-document".equals(directory)) {
            resource = fileStorageService.loadTr01DocumentAsResource(fileName);
        } else {
            // Invalid directory
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Directory");
        }
        if ("TR_Document".equals(tr01)) {
            resource = fileStorageService.loadTr01DocumentAsResource(fileName);
        }   else if ("TR-01".equals(tr01)) {
            resource = fileStorageService.loadTr01DocumentAsResource(fileName);
        } else {
            // Invalid directory
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid Directory");
        }
        if (resource == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }


        // Try to determine file's content type
        String contentType;
//        try {
//            contentType = Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
//        } catch (IOException e) {
//            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
//
//        // Fallback to octet-stream if content type could not be determined
//        if (contentType == null) {
//            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
//        }
        try {
            if (resource.exists()) {
                contentType = Files.probeContentType(Path.of(resource.getFile().getAbsolutePath()));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found" + fileName);
            }
        } catch (FileNotFoundException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        } catch (IOException e) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
//    @PatchMapping("/{id}/status")
//    public ResponseEntity<String> updateStatus(@PathVariable("id") Long id, @RequestParam String status) {
//        File file = fileRepositories.findById(id).orElse(null);
//        if (file == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found with id: " + id);
//        }
//
//        // Update the status
//        file.setStatus(status);
//        fileRepositories.save(file);
//
//        return ResponseEntity.ok("Status updated successfully");
//    }
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> updateStatusAndComment(@PathVariable("id") Long id, @RequestBody UpdateFileRequest updateFileRequest) {
    File file = fileRepositories.findById(id).orElse(null);
    if (file == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found with id: " + id);
    }

    // Update the status
    file.setStatus(updateFileRequest.getStatus());


    // Update the comment
    file.setComments(updateFileRequest.getComments());

    file.setReason(updateFileRequest.getReason());

    fileRepositories.save(file);

    return ResponseEntity.ok("Status and comment updated successfully");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable Long id) {
        // Check if the file exists
        File file = fileRepositories.findById(id).orElse(null);
        if (file == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found with id: " + id);
        }

        // Delete the file from the storage
        boolean deleted = fileStorageService.deleteFile(file.getFileName());
        if (!deleted) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file: " + file.getFileName());
        }

        // Delete the file record from the database
        fileRepositories.delete(file);

        return ResponseEntity.ok("File deleted successfully");
    }
}
