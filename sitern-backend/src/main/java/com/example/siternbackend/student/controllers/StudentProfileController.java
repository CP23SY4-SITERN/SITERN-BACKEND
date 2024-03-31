package com.example.siternbackend.student.controllers;

import com.example.siternbackend.company.DTOS.CompanyDTO;
import com.example.siternbackend.student.entities.StudentProfile;
import com.example.siternbackend.student.exception.StudentProfileNotFoundException;
import com.example.siternbackend.student.repositories.StudentProfileRepository;
import com.example.siternbackend.student.services.StudentProfileService;
import com.example.siternbackend.user.entities.User;
import com.example.siternbackend.user.repositories.UserRepository;
import jakarta.persistence.PostLoad;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityNotFoundException;


import java.util.List;

@RestController
@RequestMapping("/api/student-profiles")
public class StudentProfileController {
    @Autowired
    private StudentProfileService studentProfileService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @PostMapping
    public ResponseEntity<StudentProfile> createOrUpdateStudentProfile(@RequestBody StudentProfile studentProfile, @RequestParam Integer userId) {
        ResponseEntity<StudentProfile> response = studentProfileService.createOrUpdateStudentProfile(studentProfile, userId);
        return response;
    }

    @GetMapping
    public ResponseEntity<List<StudentProfile>> getAllStudentProfiles() {
        try {
            List<StudentProfile> studentProfiles = studentProfileService.getAllStudentProfiles();
            return ResponseEntity.ok(studentProfiles);
        } catch (StudentProfileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<StudentProfile> getStudentProfileById(@PathVariable Integer id) {
        StudentProfile studentProfile = studentProfileService.getStudentProfileById(id);
        return ResponseEntity.ok(studentProfile);
    }
//    @GetMapping
//    public ResponseEntity<List<StudentProfile>> getAllStudentProfiles() {
//        try {
//            List<StudentProfile> studentProfiles = studentProfileService.getAllStudentProfiles();
//            return ResponseEntity.ok(studentProfiles);
//        } catch (StudentProfileNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
    @DeleteMapping("/{id}")
    public void deleteStudentProfileById(@PathVariable Integer id, HttpServletRequest request) {
        studentProfileService.deleteStudentProfileById(id, request);
    }
}
