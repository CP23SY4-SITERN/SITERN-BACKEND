package com.example.siternbackend.repositories.company;

import com.example.siternbackend.entities.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}