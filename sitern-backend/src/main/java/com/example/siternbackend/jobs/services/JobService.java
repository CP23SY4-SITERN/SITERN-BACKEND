package com.example.siternbackend.jobs.services;

import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.util.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobService {
    @Autowired
    private JobPostRepository jobPostRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public JobService() {
    }

    //get ALL JOB
    public List<JobPostDTO> getAllJobs() {
        List<JobPost> jobPost = jobPostRepository.findAll(Sort.by("createdDate").descending());
        return listMapper.mapList(jobPost,JobPostDTO.class,modelMapper);
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
