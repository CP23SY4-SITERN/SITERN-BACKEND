package com.example.siternbackend.repositories.student;

import com.example.siternbackend.entities.student.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Integer> {
}