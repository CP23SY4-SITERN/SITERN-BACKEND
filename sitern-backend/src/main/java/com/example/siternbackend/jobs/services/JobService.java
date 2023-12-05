package com.example.siternbackend.jobs.services;

<<<<<<< Updated upstream:sitern-backend/src/main/java/com/example/siternbackend/services/job/JobService.java
import com.example.siternbackend.entities.job.JobPost;
import com.example.siternbackend.jobs.controllers.JobController;
import com.example.siternbackend.repositories.job.JobPostRepository;
=======
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.mapper.ListMapper;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
>>>>>>> Stashed changes:sitern-backend/src/main/java/com/example/siternbackend/jobs/services/JobService.java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {
    @Autowired
    private final JobPostRepository jobPostRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;


    @Autowired
    public JobService(JobPostRepository jobPostRepository) {
        this.jobPostRepository = jobPostRepository;
    }




    public Page<JobPost> getAllJobs(Pageable pageable) {
        return jobPostRepository.findAll(pageable);
    }


    @GetMapping
    public List<JobPostDTO> getAllJobs(HttpServletRequest request) {
            List<JobPostDTO> JobList = jobPostRepository.findAll(Sort.by("createdDate").descending());
         return listMapper.mapList();
    }
    private static final Logger log = LoggerFactory.getLogger(JobController.class);

    public Optional<JobPost> getJobById(int id) {
        return jobPostRepository.findById(id);
    }
}
