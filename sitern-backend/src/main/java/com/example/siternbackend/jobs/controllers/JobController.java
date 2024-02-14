package com.example.siternbackend.jobs.controllers;

import com.example.siternbackend.Exception.EnumValidationException;
import com.example.siternbackend.Validation.EnumValid;
import com.example.siternbackend.jobs.dtos.CreatingJobDTO;
import com.example.siternbackend.jobs.dtos.EditJobDTO;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.entities.WorkType;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
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
@RequestMapping("/api/jobs")
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
    public List<JobPostDTO> getAllJobPosts() {
        try {
            List<JobPostDTO> jobPostDTOs = jobService.getAllJobs();
            log.info("Retrieved {} job posts", jobPostDTOs.size());
            return jobPostDTOs;
        } catch (Exception e) {
            log.error("Error while retrieving job posts", e);
            throw e; // Rethrow the exception or handle it appropriately
        }
    }

    private static final Logger log = LoggerFactory.getLogger(JobController.class);
    @GetMapping("/{id}")
    public ResponseEntity<JobPostDTO> getJobById(@PathVariable int id) {
        try {
            JobPostDTO jobPostDTO = jobService.getJobPostById(id);

            if (jobPostDTO != null) {
                return new ResponseEntity<>(jobPostDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Handle exceptions, log the error, and return an appropriate response
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<JobPost> createJob(@Valid @EnumValid(enumClass = WorkType.class) @RequestBody CreatingJobDTO newJob, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException,RuntimeException {
//        log.info("POST mapping for creating a job posting");
//        System.out.println("postmapping");
//        return jobService.create(newJob, request);
//    }
    @ExceptionHandler(EnumValidationException.class)
    public ResponseEntity<String> handleEnumValidationException(EnumValidationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> createJob(@Valid @EnumValid(enumClass = WorkType.class) @RequestBody CreatingJobDTO newJob, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException, RuntimeException {
        log.info("POST mapping for creating a job posting");
        try {
            return jobService.create(newJob, request);
        } catch (EnumValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobPostById(@PathVariable Integer id, HttpServletRequest request){
        try {
            jobService.deleteJobPostById(id, request);
            // Log success message and related information
            log.info("Delete Job Successful for ID: {}", id);
            // You can log additional information if needed, e.g., request details
            log.debug("Request details - Method: {}, URI: {}", request.getMethod(), request.getRequestURI());
            // Return a success message to the client
            String successMessage = "Delete Job Successful for ID: " + id;
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } catch (Exception e) {
            // Log any exceptions that might occur during the delete operation
            log.error("Error deleting job with ID: {}", id, e);
            // Return an error message to the client
            return new ResponseEntity<>("Error deleting job with ID: " + id +"    ," + id + "Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{jobId}")
    public ResponseEntity<EditJobDTO> editJob(@PathVariable Integer jobId,@RequestBody @Valid EditJobDTO editJobDTO) {
        EditJobDTO updatedJob = jobService.editJob(jobId, editJobDTO);
        return ResponseEntity.ok(updatedJob);
    }



}
