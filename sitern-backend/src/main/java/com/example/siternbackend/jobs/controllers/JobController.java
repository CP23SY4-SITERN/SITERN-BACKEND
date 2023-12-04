package com.example.siternbackend.jobs.controllers;

import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.jobs.services.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private final JobService jobService;
    @Autowired
    private final JobPostRepository jobPostRepository;

    public JobController(JobService jobService,JobPostRepository jobPostRepository) {
        this.jobService = jobService;
        this.jobPostRepository=  jobPostRepository;
    }

    @GetMapping
    public List<JobPost> getAllJobs() {
        try {
            List<JobPost> jobPosts = jobPostRepository.findAll();
            log.info("Retrieved {} job posts", jobPosts.size());
            return jobPosts;
        } catch (Exception e) {
            log.error("Error while retrieving job posts", e);
            throw e; // rethrow the exception or handle it appropriately
        }
    }

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
    @GetMapping("/{id}")
    public ResponseEntity<JobPost> getJobById(@PathVariable int id) {
        try {
            Optional<JobPost> jobPostOptional = jobService.getJobById(id);

            if (jobPostOptional.isPresent()) {
                return new ResponseEntity<>(jobPostOptional.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions, log the error, and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
