package com.example.siternbackend.student.controllers;

import com.example.siternbackend.student.entities.StudentProfile;
import com.example.siternbackend.student.services.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/student-profiles")
public class StudentProfileController {
    @Autowired
    private StudentProfileService studentProfileService;

    @PostMapping
    public ResponseEntity<StudentProfile> createOrUpdateStudentProfile(@Validated @RequestBody StudentProfile studentProfile, HttpServletRequest request)
            throws MethodArgumentNotValidException {
        return studentProfileService.createOrUpdateStudentProfile(studentProfile, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentProfile> getStudentProfileById(@PathVariable Integer id) {
        StudentProfile studentProfile = studentProfileService.getStudentProfileById(id);
        return ResponseEntity.ok(studentProfile);
    }

    @DeleteMapping("/{id}")
    public void deleteStudentProfileById(@PathVariable Integer id, HttpServletRequest request) {
        studentProfileService.deleteStudentProfileById(id, request);
    }
}
