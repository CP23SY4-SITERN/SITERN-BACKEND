package com.example.siternbackend.company.controllers;

import com.example.siternbackend.company.DTOS.CompanyDTO;
import com.example.siternbackend.company.repositories.CompanyRepository;
import com.example.siternbackend.company.services.CompanyService;
import com.example.siternbackend.jobs.controllers.JobController;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.jobs.services.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private  CompanyRepository companyRepository;

    public CompanyController(CompanyService companyService,CompanyRepository companyRepository) {
        this.companyService = companyService;
        this.companyRepository=  companyRepository;
    }

    @GetMapping
    public List<CompanyDTO> getAllJobPosts() {
        try {
            List<CompanyDTO> companyDTOS = companyService.getAllCompanies();
            log.info("Retrieved {} company list", companyDTOS.size());
            return companyDTOS;
        } catch (Exception e) {
            log.error("Error while retrieving company list", e);
            throw e; // Rethrow the exception or handle it appropriately
        }
    }
    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
}
