//package com.example.siternbackend.files.controllers;
//
//import com.example.siternbackend.files.entities.File;
//import com.example.siternbackend.files.services.FileService;
//import jakarta.annotation.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/files")
//public class FileController {
//
//    @Autowired
//    private FileService fileService;
//    @Autowired
//    private final StorageService storageService;
//
////    @PostMapping("/upload")
////    public ResponseEntity<File> uploadFile(
////            @RequestParam("file") MultipartFile file,
////            @RequestParam(value = "uploadDirectory", required = false, defaultValue = "/home/sysadmin/files/TR_Document") String uploadDirectory
////    ) {
////        try {
////            File savedFile = fileService.saveFile(file, uploadDirectory);
////            return ResponseEntity.ok(savedFile);
////        } catch (IOException e) {
////            e.printStackTrace();
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
////        }
////    }
//
//    @Autowired
//    public FileController(FileService fileService,StorageService storageService) {
//        this.fileService = fileService;
//        this.storageService = storageService;
//    }
//
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
//}
