package com.example.siternbackend.company.DTOS;

import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.entities.JobLocation;
import lombok.Data;

import java.util.Set;

@Data
public class CompanyWithJobLocationDTO {
    private String companyName;
    private String companyDescription;
    private String companyWebsite;
    private String companyLocation;
    private Integer companyEmployee;
    private Set<JobLocationDTO> jobLocations;
}
