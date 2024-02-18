package com.example.siternbackend.company.repositories;

import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.jobs.entities.JobLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

}