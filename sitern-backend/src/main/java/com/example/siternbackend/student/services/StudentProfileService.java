package com.example.siternbackend.student.services;


import com.example.siternbackend.student.entities.StudentProfile;
import com.example.siternbackend.student.repositories.StudentProfileRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Service
public class StudentProfileService {
    @Autowired
    private StudentProfileRepository studentProfileRepository;

    public ResponseEntity<StudentProfile> createOrUpdateStudentProfile(@Valid StudentProfile studentProfile, HttpServletRequest request) throws MethodArgumentNotValidException {
        StudentProfile existingProfile = studentProfileRepository.findById(studentProfile.getId()).orElse(null);

        if (existingProfile != null) {
            // Update existing profile
            BeanUtils.copyProperties(studentProfile, existingProfile, getNullPropertyNames(studentProfile));
        } else {
            // Create new profile
            existingProfile = studentProfile;
        }

        studentProfileRepository.saveAndFlush(existingProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(existingProfile);
    }

    public StudentProfile getStudentProfileById(Integer id) {
        return studentProfileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Student profile with id " + id + " does not exist"
                ));
    }

    public void deleteStudentProfileById(Integer id, HttpServletRequest request) {
        studentProfileRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Student profile with id " + id + " not found"));
        studentProfileRepository.deleteById(id);
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> nullProperties = new HashSet<>();

        for (java.beans.PropertyDescriptor pd : pds) {
            if (src.getPropertyValue(pd.getName()) == null) {
                nullProperties.add(pd.getName());
            }
        }

        return nullProperties.toArray(new String[0]);
    }
}

