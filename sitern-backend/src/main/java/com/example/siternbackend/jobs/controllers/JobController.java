package com.example.siternbackend.jobs.controllers;

import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.entities.job.JobPost;
import com.example.siternbackend.repositories.job.JobPostRepository;
import com.example.siternbackend.jobs.services.JobService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    @Autowired
    private final JobService jobService;
    @Autowired
    private final JobPostRepository jobPostRepository;
    // Assuming you have a mapper for JobPost to JobPostDTO

    public JobController(JobService jobService, JobPostRepository jobPostRepository) {
        this.jobService = jobService;
        this.jobPostRepository = jobPostRepository;
    }

    @GetMapping
    public ResponseEntity<List<JobPostDTO>> getAllJobs(HttpServletRequest request) {
        try {
            List<JobPostDTO> jobPage = jobService.getAllJobs(request);

            log.info("Retrieved {} job posts", jobPage.size());

            if (jobPage.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(jobPage, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while retrieving job posts", e);
            // Handle exceptions, log the error, and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<JobPostDTO> getJobById(@PathVariable int id) {
//        try {
//            Optional<JobPost> jobPostOptional = jobService.getJobById(id);
//
//            if (jobPostOptional.isPresent()) {
//                JobPostDTO jobDTO = jobPostMapper.toDTO(jobPostOptional.get());
//                return new ResponseEntity<>(jobDTO, HttpStatus.OK);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//        } catch (Exception e) {
//            // Handle exceptions, log the error, and return an appropriate response
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
}

