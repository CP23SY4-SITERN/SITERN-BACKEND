package com.example.siternbackend.jobs.controllers;

import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.repositories.JobLocationRepository;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.jobs.services.JobLocationService;
import com.example.siternbackend.jobs.services.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/jobLocation")
public class JobLocationController {
    @Autowired
    private final JobLocationService jobLocationService;
    @Autowired
    private final JobLocationRepository jobLocationRepository;

    public JobLocationController(JobLocationService jobLocationService,JobLocationRepository jobLocationRepository) {
        this.jobLocationService = jobLocationService;
        this.jobLocationRepository=  jobLocationRepository;
    }

    @GetMapping
    public List<JobLocationDTO> getAllJobLocations() {
        try {
            List<JobLocationDTO> jobLocationDTO = jobLocationService.getAllJobLocations();
            log.info("Retrieved {} job locations", jobLocationDTO.size());
            return jobLocationDTO;
        } catch (Exception e) {
            log.error("Error while retrieving job locations", e);
            throw e; // Rethrow the exception or handle it appropriately
        }
    }

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
}
