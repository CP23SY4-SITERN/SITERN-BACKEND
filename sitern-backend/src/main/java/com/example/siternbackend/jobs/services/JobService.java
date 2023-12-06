package com.example.siternbackend.jobs.services;

import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {
    private final JobPostRepository jobPostRepository;

    @Autowired
    public JobService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }

    //get ALL JOB
    public List<JobPostDTO> getAllJobs() {
        List<JobPost> jobPost = jobPostRepository.findAll();
        return jobPost.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    private JobPostDTO convertToDTO(JobPost jobPost) {
        JobPostDTO jobPostDTO = new JobPostDTO();
        jobPostDTO.setId(jobPost.getId());
        jobPostDTO.setCompany_ID(jobPost.getCompany_ID());
        // Set other properties
        return jobPostDTO;
    }

    //GET JOB BY ID
    public JobPostDTO getJobPostById(Integer id) {
        JobPost jobPostEntity = jobPostRepository.findById(id).orElse(null);
        return jobPostEntity != null ? convertToDTO(jobPostEntity) : null;
    }

    private JobPost convertToEntity(JobPostDTO jobPostDTO) {
        JobPost jobPost = new JobPost();
        jobPost.setId(jobPostDTO.getId());
        jobPost.setCompany_ID(jobPostDTO.getCompany_ID());
        // Set other properties
        return jobPost;
    }



}
