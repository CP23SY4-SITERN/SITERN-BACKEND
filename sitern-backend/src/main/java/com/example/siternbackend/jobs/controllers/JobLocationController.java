package com.example.siternbackend.jobs.controllers;

import com.example.siternbackend.jobs.dtos.CreatingJobDTO;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobLocationRepository;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.jobs.services.JobLocationService;
import com.example.siternbackend.jobs.services.JobService;
import jakarta.persistence.EntityNotFoundException;
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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobLocationById(@PathVariable Integer id, HttpServletRequest request){
        try {
            jobLocationService.deleteJobLocationById(id, request);
            // Log success message and related information
            log.info("Delete Job Location Successful for ID: {}", id);
            // You can log additional information if needed, e.g., request details
            log.debug("Request details - Method: {}, URI: {}", request.getMethod(), request.getRequestURI());

            // Return a success message to the client
            String successMessage = "Delete Job Location Successful for ID: " + id;
            return new ResponseEntity<>(successMessage, HttpStatus.OK);
        } catch (Exception e) {
            // Log any exceptions that might occur during the delete operation
            log.error("Error deleting job location with ID: {}", id, e);
            // Return an error message to the client
            return new ResponseEntity<>("Error deleting job location with ID: " + id, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{companyName}")
    public ResponseEntity<List<JobLocation>> getAllJobLocationFromCompanyName(
            @PathVariable String companyName,
            HttpServletRequest request) {
        try {
            List<JobLocation> jobLocations = jobLocationService.getAllJobLocationFromCompanyName(companyName);
            // Log success message and related information
            log.info("Get Job Locations Successful for Company Name: {}", companyName);
            // You can log additional information if needed, e.g., request details
            log.debug("Request details - Method: {}, URI: {}", request.getMethod(), request.getRequestURI());

            // Return the list of job locations to the client
            return new ResponseEntity<>(jobLocations, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Log the exception and return an error message to the client
            log.error("Error getting job locations for Company Name: {}", companyName, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Log any other exceptions that might occur
            log.error("Unexpected error getting job locations for Company Name: {}", companyName, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/company/{id}")
    public ResponseEntity<List<JobLocation>> getAllJobLocationFromCompanyId(
            @PathVariable Integer id,
            HttpServletRequest request) {
        try {
            List<JobLocation> jobLocations = jobLocationService.getAllJobLocationFromCompanyId(id);
            // Log success message and related information
            log.info("Get Job Locations Successful for Company ID: {}", id);
            // You can log additional information if needed, e.g., request details
            log.debug("Request details - Method: {}, URI: {}", request.getMethod(), request.getRequestURI());

            // Return the list of job locations to the client
            return new ResponseEntity<>(jobLocations, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            // Log the exception and return an error message to the client
            log.error("Error getting job locations for Company ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Log any other exceptions that might occur
            log.error("Unexpected error getting job locations for Company ID: {}", id, e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
