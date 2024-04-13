package com.example.siternbackend.files.repositories;


import com.example.siternbackend.files.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepositories extends JpaRepository<File, Long> {
    List<File> findAllByUserId(Integer userId);
}
