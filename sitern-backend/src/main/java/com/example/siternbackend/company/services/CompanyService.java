package com.example.siternbackend.company.services;

import com.example.siternbackend.company.DTOS.CompanyDTO;
import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.company.repositories.CompanyRepository;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.util.ListMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.validation.Valid;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;

    public CompanyService() {
    }

    //get ALL JOB
    public List<CompanyDTO> getAllCompanies() {
        List<Company> companyList = companyRepository.findAll();
        return listMapper.mapList(companyList, CompanyDTO.class, modelMapper);
    }


    //    //GET JOB BY ID
//    public CompanyDTO getCompanyListById(Integer id) {
//        Company companyList = companyRepository.findById(id).orElse(null);
//        return companyList != null ? convertToDTO(companyList) : null;
//    }
//
//    private JobPost convertToEntity(JobPostDTO jobPostDTO) {
//        JobPost jobPost = new JobPost();
//        jobPost.setId(jobPostDTO.getId());
//        jobPost.setCompany_ID(jobPostDTO.getCompany_ID());
//        // Set other properties
//        return jobPost;
//    }
//
    public ResponseEntity<Company> create(@RequestBody @Valid CompanyDTO newCompany, HttpServletRequest request) throws MethodArgumentNotValidException,MessagingException, IOException {

        Company c = modelMapper.map(newCompany, Company.class);
        companyRepository.saveAndFlush(c);
        System.out.println("Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }
}
