package com.example.siternbackend.jobs.controllers;

import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/jobs")
public class JobController {

    private JobPostRepository jobPostRepository;

    public JobController(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    @GetMapping
    public List<JobPost> getAllJobs(){
        return jobPostRepository.findAll();
    }
}
