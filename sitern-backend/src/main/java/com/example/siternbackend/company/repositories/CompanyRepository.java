package com.example.siternbackend.company.repositories;

import com.example.siternbackend.company.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}