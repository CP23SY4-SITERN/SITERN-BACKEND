package com.example.siternbackend.company.services;

import com.example.siternbackend.company.DTOS.CompanyDTO;
import com.example.siternbackend.company.DTOS.CompanyWithJobLocationDTO;
import com.example.siternbackend.company.DTOS.EditCompanyDTO;
import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.company.repositories.CompanyRepository;
import com.example.siternbackend.jobs.dtos.EditJobDTO;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.dtos.JobPostDTO;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.jobs.entities.JobPost;
import com.example.siternbackend.jobs.repositories.JobPostRepository;
import com.example.siternbackend.jobs.services.JobService;
import com.example.siternbackend.util.ListMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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


    //    //GET COMPANY BY ID
    public Company getCompanyByID(Integer id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, " id " + id +
                        "Does Not Exist !!!"
                ));
        return modelMapper.map(company, Company.class);
    }
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
        // Assuming jobLocationDTO is part of CompanyDTO or available in the request
//        company.setJobLocationsFromDTO(newCompany.getJobLocations());
        companyRepository.saveAndFlush(c);
        System.out.println("Created");
        return ResponseEntity.status(HttpStatus.CREATED).body(c);
    }
    public List<CompanyWithJobLocationDTO> getAllCompaniesWithJobLocations() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(this::mapToCompanyWithJobLocationDTO)
                .collect(Collectors.toList());
    }
    private CompanyWithJobLocationDTO mapToCompanyWithJobLocationDTO(Company company) {
        CompanyWithJobLocationDTO dto = new CompanyWithJobLocationDTO();
        dto.setCompanyName(company.getCompanyName());
        dto.setCompanyDescription(company.getCompanyDescription());
        dto.setCompanyWebsite(company.getCompanyWebsite());
        dto.setCompanyLocation(company.getCompanyLocation());
        dto.setCompanyEmployee(company.getCompanyEmployee());

        Set<JobLocationDTO> jobLocationDTOs = company.getJobLocations().stream()
                .map(this::mapToJobLocationDTO)
                .collect(Collectors.toSet());
        dto.setJobLocations(jobLocationDTOs);

        return dto;
    }
    private JobLocationDTO mapToJobLocationDTO(JobLocation jobLocation) {
        JobLocationDTO dto = new JobLocationDTO();
        dto.setId(jobLocation.getId());
        dto.setRoad(jobLocation.getRoad());
        dto.setSubDistrict(jobLocation.getSubDistrict());
        dto.setProvince(jobLocation.getProvince());
        dto.setCountry(jobLocation.getCountry());
        dto.setZip(jobLocation.getZip());
        return dto;
    }
    public void deleteCompanyById(Integer id,HttpServletRequest  request) {
        companyRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can't Found This Company"));
        companyRepository.deleteById(id);

    }

//    public EditCompanyDTO editCompany(HttpServletRequest request, EditCompanyDTO editCompany, Integer id) {
//          Company company = companyRepository.findById(id).map(o->mapCompany(o, editCompany))
//                    .orElseThrow(() ->
//                            new ResponseStatusException(HttpStatus.FORBIDDEN, "No ID : " + id));
//            companyRepository.saveAndFlush(company);
//            return modelMapper.map(company, EditCompanyDTO.class);
//    }
//
//        private Company mapCompany(Company existingCompany, EditCompanyDTO updateCompany) {
//            existingCompany.setCompanyName(updateCompany.getCompanyName());
//            existingCompany.setCompanyDescription(updateCompany.getCompanyDescription());
//            existingCompany.setCompanyLocation(updateCompany.getCompanyLocation());
//            existingCompany.setCompanyWebsite(updateCompany.getCompanyWebsite());
//            existingCompany.setCompanyEmployee(updateCompany.getCompanyEmployee());
//            return existingCompany;
//        }



    public EditCompanyDTO editCompany(HttpServletRequest request, EditCompanyDTO editCompany, int id) {
        Company existingCompany = companyRepository.findById(id)
                .orElseThrow(() -> new JobService.ResourceNotFoundException("Company not found with id " + id));

        // Copy non-null properties from editCompany to existingCompany
        BeanUtils.copyProperties(editCompany, existingCompany, getNullPropertyNames(editCompany));

        // Save the updated company
        Company updatedCompany = companyRepository.save(existingCompany);

        // Convert and return the updated company as DTO
        return convertToEditDto(updatedCompany);
    }

    private EditCompanyDTO convertToEditDto(Company company) {
        EditCompanyDTO editCompanyDTO = new EditCompanyDTO();
        BeanUtils.copyProperties(company, editCompanyDTO);
        return editCompanyDTO;
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> nullProperties = new HashSet<>();

        for (java.beans.PropertyDescriptor pd : pds) {
            if (src.getPropertyValue(pd.getName()) == null) {
                nullProperties.add(pd.getName());
            }
        }

        return nullProperties.toArray(new String[0]);
    }


}
