package com.example.siternbackend.student.repositories;

import com.example.siternbackend.student.entities.StudentProfile;
import com.example.siternbackend.student.entities.StudentProfileId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, StudentProfileId> {
}