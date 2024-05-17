package com.example.siternbackend.files.controllers;

import com.example.siternbackend.files.entities.File;
import com.example.siternbackend.files.services.FileService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

//    @GetMapping
//    public ResponseEntity<List<File>> getAllFiles() {
//        List<File> files = fileService.getAllFiles();
//        return new ResponseEntity<>(files, HttpStatus.OK);
//    }
//    @GetMapping("/with")
//    public ResponseEntity<List<FileResponse>> getAllFilesWithUserId() {
//        List<FileResponse> fileResponses = fileService.getAllFilesWithUserId();
//        return new ResponseEntity<>(fileResponses, HttpStatus.OK);
//    }
    @GetMapping("/name")
    public ResponseEntity<List<FileResponse>> getAllFilesWithStdName() {
        List<FileResponse> fileResponses = fileService.getAllFilesWithStdName();
        return new ResponseEntity<>(fileResponses, HttpStatus.OK);
    }
//    @GetMapping
//    public ResponseEntity<List<FileResponse>> getAllFiles() {
//        List<File> files = fileService.getAllFiles();
//        List<FileResponse> fileResponses = files.stream()
//                .map(file -> {
//                    String stdName = extractStdName(file.getFileName()); // Assuming you have a method to extract std name from file name
//                    return new FileResponse(file.getId(), file.getFileName(), file.getFilePath(), file.getUploadedDate(), file.getStatus(), stdName,file.getComment());
//                })
//                .collect(Collectors.toList());
//
//        // Sort the list of FileResponse objects by std name
//        fileResponses.sort(Comparator.comparing(FileResponse::getStdName));
//
//        return new ResponseEntity<>(fileResponses, HttpStatus.OK);
//    }
@GetMapping
public ResponseEntity<List<FileResponse>> getAllFiles() {
    List<File> files = fileService.getAllFiles();

    // Create a map to store the latest file for each stdName
    Map<String, File> latestFilesMap = new HashMap<>();

    // Iterate through each file and update the latest file for each stdName
    for (File file : files) {
        String stdName = extractStdName(file.getFileName());
        if (!latestFilesMap.containsKey(stdName) || file.getUploadedDate().after(latestFilesMap.get(stdName).getUploadedDate())) {
            latestFilesMap.put(stdName, file);
        }
    }

    // Convert the latest files map to a list of FileResponse objects
    List<FileResponse> fileResponses = latestFilesMap.values().stream()
            .map(file -> new FileResponse(file.getId(), file.getFileName(), file.getFilePath(), file.getUploadedDate(), file.getStatus(), extractStdName(file.getFileName()), file.getComments(), file.getReason()))
            .collect(Collectors.toList());

    // Sort the list of FileResponse objects by stdName
    fileResponses.sort(Comparator.comparing(FileResponse::getStdName));

    return new ResponseEntity<>(fileResponses, HttpStatus.OK);
}
    // Method to extract std name from file name
    private String extractStdName(String fileName) {
        // Your logic to extract std name from file name, e.g., using regular expressions
        // For example, assuming std name is after "-" character
        int indexOfDash = fileName.indexOf("-");
        if (indexOfDash != -1 && indexOfDash + 1 < fileName.length()) {
            return fileName.substring(0, indexOfDash);
        }
        return "";
    }

//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                        path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
//                                "serveFile", path.getFileName().toString()).build().toUri().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }
//
//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//
//        if (file == null)
//            return ResponseEntity.notFound().build();
//
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }
//
//    @PostMapping("/")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
//
//        storageService.store(file);
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
//
//        return "redirect:/";
//    }
//
//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }
}
