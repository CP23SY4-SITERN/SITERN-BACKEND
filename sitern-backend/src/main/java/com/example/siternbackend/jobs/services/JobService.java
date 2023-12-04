package com.example.siternbackend.jobs.services;

import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    private final JobPostRepository jobPostRepository;

    @Autowired
    public JobService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    public List<JobPost> getAllJobs() {
        return jobPostRepository.findAll();
    }

//    public Page<JobPost> getAllJobs(Pageable pageable) {
//        return jobPostRepository.findAll(pageable);
//    }

    public Optional<JobPost> getJobById(int id) {
        return jobPostRepository.findById(id);
    }

}
