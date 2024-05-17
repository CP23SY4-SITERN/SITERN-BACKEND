package com.example.siternbackend.jobs.services;

import com.example.siternbackend.company.DTOS.EditCompanyDTO;
import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.company.repositories.CompanyRepository;
import com.example.siternbackend.company.services.CompanyService;
import com.example.siternbackend.jobs.dtos.CreatingJobDTO;
import com.example.siternbackend.jobs.dtos.EditJobDTO;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobLocationRepository;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.util.ListMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.*;

@Service
public class JobService {
    @Autowired
    private JobPostRepository jobPostRepository;
    @Autowired
    private JobLocationRepository jobLocationRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ConversionService conversionService;
    @Autowired
    private CompanyService companyService;

    public JobService() {
    }

    //get ALL JOB
//    public List<JobPostDTO> getAllJobs() {
//        List<JobPost> jobPost = jobPostRepository.findAll(Sort.by("createdDate").descending());
//        return listMapper.mapList(jobPost,JobPostDTO.class,modelMapper);
//    }

    public List<JobPostDTO> getAllJobs() {
        List<JobPost> jobPosts = jobPostRepository.findAll(Sort.by("createdDate").descending());

        List<JobPostDTO> jobPostDTOs = listMapper.mapList(jobPosts, JobPostDTO.class, modelMapper);

        // Populate the zip attribute from JobLocation
        for (JobPostDTO jobPostDTO : jobPostDTOs) {
            JobLocationDTO jobLocationDTO = jobPostDTO.getJobLocation();
            if (jobLocationDTO != null) {
                jobPostDTO.setZip(jobLocationDTO.getZip());
            }

            // Set the company_id...
            Integer companyId = jobPostDTO.getCompany_id();
            if (companyId == null) {
                // Extract company_id from the associated Company entity in JobPost
                Company company = jobPostRepository.getOne(jobPostDTO.getId()).getCompany();
                if (company != null) {
                    jobPostDTO.setCompany_id(company.getId());
                }
            }
        }return jobPostDTOs;
    }
//public List<JobPostDTO> getAllJobsPaginated(int page, int size) {
//    // Calculate the offset based on the page number and size
//    int offset = page * size;
//
//    // Retrieve job posts based on the calculated offset and size, sorted by created date in descending order
//    List<JobPost> jobPosts = jobPostRepository.findAll(PageRequest.of(page, size, Sort.by("createdDate").descending())).getContent();
//
//    // Map job posts to DTOs
//    List<JobPostDTO> jobPostDTOs = listMapper.mapList(jobPosts, JobPostDTO.class, modelMapper);
//
//    // Populate the zip attribute from JobLocation
//    for (JobPostDTO jobPostDTO : jobPostDTOs) {
//        JobLocationDTO jobLocationDTO = jobPostDTO.getJobLocation();
//        if (jobLocationDTO != null) {
//            jobPostDTO.setZip(jobLocationDTO.getZip());
//        }
//
//        // Set the company_id
//        Integer companyId = jobPostDTO.getCompany_id();
//        if (companyId == null) {
//            // Extract company_id from the associated Company entity in JobPost
//            Company company = jobPostRepository.getOne(jobPostDTO.getId()).getCompany();
//            if (company != null) {
//                jobPostDTO.setCompany_id(company.getId());
//            }
//        }
//    }
//
//    return jobPostDTOs;
//}






    //    private JobPostDTO convertToDTO(JobPost jobPost) {
//        JobPostDTO jobPostDTO = new JobPostDTO();
//        jobPostDTO.setId(jobPost.getId());
//        jobPostDTO.setCompany_ID(jobPost.getCompany_ID());
//        // Set other properties
//        return jobPostDTO;
//    }

    //GET JOB BY ID
    public JobPostDTO getJobPostById(Integer id) {
        JobPost jobPost = jobPostRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, " id " + id +
                "Does Not Exist !!!"
        ));
        return modelMapper.map(jobPost, JobPostDTO.class);
    }

    private JobPost convertToEntity(JobPostDTO jobPostDTO) {
        JobPost jobPost = new JobPost();
        jobPost.setId(jobPostDTO.getId());
        jobPost.setCompany(companyService.getCompanyByID(jobPostDTO.getCompany_id()));
        // Set other properties
        return jobPost;
    }

//    public ResponseEntity create(CreatingJobDTO newJob, HttpServletRequest request) throws MessagingException, IOException {
//        newJob.setCreatedDate(ZonedDateTime.now().toLocalDateTime());
//        System.out.println("createdDate");
//        Company company = companyRepository.findById(newJob.getCompany_ID())
//                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + newJob.getCompany_ID()));
//        System.out.println("company found");
//        JobPost j = modelMapper.map(newJob, JobPost.class);
//        j.setCompany(company);
//        jobPostRepository.saveAndFlush(j);
//
//        System.out.println("Created");
//        return ResponseEntity.status(HttpStatus.CREATED).body(j);
//    }
    public List<JobLocation> findJobLocationsById(Integer jobLocationId) {
    List<JobLocation> jobLocations = jobLocationRepository.findAllById(Collections.singletonList(jobLocationId));

    if (!jobLocations.isEmpty()) {
        return jobLocations;
    } else {
        throw new EntityNotFoundException("JobLocation not found with ID: " + jobLocationId);
    }
}
    public ResponseEntity create(CreatingJobDTO newJob, HttpServletRequest request) throws MessagingException, IOException {
        Company company = companyRepository.findById(newJob.getCompany_ID())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + newJob.getCompany_ID()));
        List<JobLocation> jobLocations = findJobLocationsById(newJob.getJobLocation_ID());

        if (jobLocations.isEmpty()) {
            throw new EntityNotFoundException("JobLocation not found with ID: " + newJob.getJobLocation_ID());
        }
        JobPost j = modelMapper.map(newJob, JobPost.class);
        System.out.println("company found");
        j.setCompany(company);
        j.setJobLocation(jobLocations.get(0));
        jobPostRepository.saveAndFlush(j);
        System.out.println("Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(j);
    }

    public void deleteJobPostById(Integer id,HttpServletRequest  request) {
                jobPostRepository.findById(id).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Can't Found This Job Post"));
                jobPostRepository.deleteById(id);

    }

    public EditJobDTO editJob(Integer jobId, EditJobDTO editJobDTO) {
        JobPost existingJob = jobPostRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id " + jobId));

        // Copy non-null properties from editJobDTO to existingJob
        copyNonNullProperties(editJobDTO, existingJob);

        // Save the updated job
        JobPost updatedJob = jobPostRepository.save(existingJob);

        // Convert and return the updated job as DTO
        return convertToEditDto(updatedJob);
    }

    private void copyNonNullProperties(Object source, Object target) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        final BeanWrapper trg = new BeanWrapperImpl(target);

        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        for (java.beans.PropertyDescriptor pd : pds) {
            if (!pd.getName().equals("class")) {
                Object srcValue = src.getPropertyValue(pd.getName());
                if (srcValue != null) {
                    // Use ConversionService to convert enum values
                    if (pd.getPropertyType().isEnum()) {
                        trg.setPropertyValue(pd.getName(), conversionService.convert(srcValue, pd.getPropertyType()));
                    } else {
                        trg.setPropertyValue(pd.getName(), srcValue);
                    }
                }
            }
        }
    }


    // Convert Job entity to DTO
    private EditJobDTO convertToEditDto(JobPost job) {
        EditJobDTO editJobDTO = new EditJobDTO();
        BeanUtils.copyProperties(job, editJobDTO);
        return editJobDTO;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }


}
