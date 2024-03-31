package com.example.siternbackend.jobs.services;

import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.jobs.dtos.CreatingJobDTO;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobLocationRepository;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.util.ListMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.List;

@Service
public class JobLocationService {
    @Autowired
    private JobLocationRepository jobLocationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public JobLocationService() {
    }

    //get ALL JOB
    public List<JobLocationDTO> getAllJobLocations() {
        //ต้องเพิ่ม sortbycompanyID
        List<JobLocation> jobLocation = jobLocationRepository.findAll();
        return listMapper.mapList(jobLocation,JobLocationDTO.class,modelMapper);
    }
//    private JobLocationDTO convertToDTO(JobPost jobPost) {
//        JobLocationDTO jobLocationDTO = new JobLocationDTO();
//        jobLocationDTO.setId(jobPost.getId());
//        jobLocationDTO.setCompany_ID(jobPost.getCompany_ID());
//        // Set other properties
//        return jobLocationDTO;
//    }


//    private JobPost convertToEntity(JobPostDTO jobPostDTO) {
//        JobPost jobPost = new JobPost();
//        jobPost.setId(jobPostDTO.getId());
//        jobPost.setCompany_ID(jobPostDTO.getCompany_ID());
//        // Set other properties
//        return jobPost;
//    }

    public ResponseEntity create(JobLocationDTO newJobLocation, HttpServletRequest request) throws MessagingException, IOException {
        JobLocation j = modelMapper.map(newJobLocation, JobLocation.class);
        jobLocationRepository.saveAndFlush(j);
        System.out.println("Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(j);
    }

    public void deleteJobLocationById(Integer id,HttpServletRequest  request) {
        jobLocationRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can't Found This Job Location"));
        jobLocationRepository.deleteById(id);

    }
    public List<JobLocation> getAllJobLocationFromCompanyName(String companyName) {
        return jobLocationRepository.findByCompany_CompanyName(companyName);
    }
    public List<JobLocation> getAllJobLocationFromCompanyId(Integer id) {
        return jobLocationRepository.findByCompany_Id(id);
    }
}
