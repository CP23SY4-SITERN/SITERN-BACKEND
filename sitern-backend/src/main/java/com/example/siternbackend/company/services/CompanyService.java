package com.example.siternbackend.company.services;

import com.example.siternbackend.company.DTOS.CompanyDTO;
import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.company.repositories.CompanyRepository;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.util.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import java.util.List;

public class CompanyService {
//    @Autowired
//    private CompanyRepository companyRepository;
//    @Autowired
//    private ModelMapper modelMapper;
//    @Autowired
//    private ListMapper listMapper;
//
//    public CompanyService() {
//    }
//
//    //get ALL JOB
//    public List<CompanyDTO> getAllCompanies() {
//        List<Company> companyList = companyRepository.findAll();
//        return listMapper.mapList(companyList,CompanyDTO.class,modelMapper);
//    }
//
//
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

}
