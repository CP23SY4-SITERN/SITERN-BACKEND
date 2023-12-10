package com.example.siternbackend.jobs.services;

import com.example.siternbackend.company.DTOS.EditCompanyDTO;
import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.company.repositories.CompanyRepository;
import com.example.siternbackend.jobs.dtos.CreatingJobDTO;
import com.example.siternbackend.jobs.dtos.EditJobDTO;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobPost;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JobService {
    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ConversionService conversionService;

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
        jobPost.setCompany_ID(jobPostDTO.getCompany_ID());
        // Set other properties
        return jobPost;
    }

    public ResponseEntity create(CreatingJobDTO newJob, HttpServletRequest request) throws MessagingException, IOException {
        newJob.setCreatedDate(ZonedDateTime.now().toLocalDateTime());
        System.out.println("createdDate");
        Company company = companyRepository.findById(newJob.getCompany_ID())
                .orElseThrow(() -> new EntityNotFoundException("Company not found with ID: " + newJob.getCompany_ID()));
        System.out.println("company found");
        JobPost j = modelMapper.map(newJob, JobPost.class);
        j.setCompany(company);
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
