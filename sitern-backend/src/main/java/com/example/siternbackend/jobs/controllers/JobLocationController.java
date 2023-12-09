package com.example.siternbackend.jobs.controllers;

import com.example.siternbackend.jobs.dtos.CreatingJobDTO;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobLocationRepository;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.jobs.services.JobLocationService;
import com.example.siternbackend.jobs.services.JobService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<JobPost> createJobLocation(@Valid @RequestBody JobLocationDTO newJobLocation, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException {
        log.info("POST mapping for creating a job location");
        System.out.println("postmapping");
        return jobLocationService.create(newJobLocation, request);
    }
}
