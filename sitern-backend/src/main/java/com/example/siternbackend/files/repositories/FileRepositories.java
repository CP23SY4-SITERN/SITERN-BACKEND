package com.example.siternbackend.files.repositories;


import com.example.siternbackend.files.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepositories extends JpaRepository<File, Long> {
}
