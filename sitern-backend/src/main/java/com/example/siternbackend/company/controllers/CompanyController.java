package com.example.siternbackend.company.controllers;

import com.example.siternbackend.company.DTOS.CompanyDTO;
import com.example.siternbackend.company.DTOS.CompanyWithJobLocationDTO;
import com.example.siternbackend.company.DTOS.EditCompanyDTO;
import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.company.repositories.CompanyRepository;
import com.example.siternbackend.company.services.CompanyService;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.jobs.services.JobLocationService;
import com.example.siternbackend.jobs.services.JobService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api/companies")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private  CompanyRepository companyRepository;
    @Autowired
    private JobLocationService jobLocationService;
    @Autowired
    private ModelMapper modelMapper;
    public CompanyController(CompanyService companyService,CompanyRepository companyRepository,JobLocationService jobLocationService,ModelMapper modelMapper) {
        this.companyService = companyService;
        this.companyRepository=  companyRepository;
        this.jobLocationService = jobLocationService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CompanyDTO> getAllCompanyList() {
        try {
            List<CompanyDTO> companyDTOS = companyService.getAllCompanies();
            log.info("Retrieved {} company list", companyDTOS.size());
            return companyDTOS;
        } catch (Exception e) {
            log.error("Error while retrieving company list", e);
            throw e; // Rethrow the exception or handle it appropriately
        }
    }
    @GetMapping("/{id}/jobLocation")
    public List<JobLocation> getAllJobLocationFromCompanyId(@PathVariable Integer id) {
        return companyService.getAllJobLocationFromCompanyId(id);
    }
    @GetMapping("/{companyName}/jobLocation")
    public List<JobLocation> getAllJobLocationFromCompanyName(@PathVariable String companyName) {
        return companyService.getAllJobLocationFromCompanyName(companyName);
    }

    @GetMapping("/{company_id}/jobLocations")
    public ResponseEntity<?> getJobLocationsByCompanyId(@PathVariable Integer company_id) {
        try {
            List<JobLocation> jobLocations = companyService.getJobLocationsByCompanyId(company_id);
            return ResponseEntity.ok(jobLocations);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
//    @GetMapping("/{companyName}")
//    public List<JobLocation> getAllJobLocations(@PathVariable String companyName) {
//        return companyService.findByJobLocations(companyName);
//    }

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);
//    @PostMapping
//    @Validated
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity createCompany(@Valid @RequestBody CompanyDTO newCompany, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException {
//        return EventService.save(newEvent)
//        return ResponseEntity.ok("User is valid");
//        log.info("POST mapping for creating a company");
//        System.out.println("postmapping");
//        return companyService.create(newCompany, request);
//    }
    @PostMapping
    @Validated
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyDTO newCompany, HttpServletRequest request) throws MethodArgumentNotValidException, MessagingException, IOException {
        log.info("POST mapping for creating a company");
        System.out.println("postmapping");
        try {
            return companyService.create(newCompany, request);
        } catch (Exception e) {
            log.error("Error while creating a company", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating a company");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCompanyById(@PathVariable Integer id, HttpServletRequest request){
        companyService.deleteCompanyById(id, request);
    }
    @GetMapping("/jobLocation")
    public List<CompanyWithJobLocationDTO> getAllCompaniesWithJobLocations() {
        return companyService.getAllCompaniesWithJobLocations();
    }
//    @GetMapping("/by-job-locations-road/{road}")
//    public List<Company> getCompaniesByJobLocationsRoad(@PathVariable String road) {
//        return companyService.getCompaniesByJobLocationsRoad(road);
//    }
    @GetMapping("/{company_id}")
    public ResponseEntity<?> getCompanyWithJobLocations(@PathVariable Integer company_id) {
        try {
            Company getCompanyWithJobLocations = companyService.getCompanyWithJobLocations(company_id);
            return ResponseEntity.ok(getCompanyWithJobLocations);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/{id}")
    public ResponseEntity<EditCompanyDTO> editCompanyDTO(
            @Valid @RequestBody EditCompanyDTO editCompanyDTO,
            @PathVariable int id,
            HttpServletRequest request) {

        try {
            EditCompanyDTO updatedCompany = companyService.editCompany(request, editCompanyDTO, id);
            return ResponseEntity.ok(updatedCompany);
        } catch (JobService.ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



}
