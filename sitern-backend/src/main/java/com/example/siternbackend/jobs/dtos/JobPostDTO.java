package com.example.siternbackend.jobs.dtos;

import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.jobs.entities.JobPost;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPostDTO {
    private Integer id;
    private String title;
    private LocalDateTime createdDate;
    private Integer company_ID;
    private String companyName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String applicationDeadline;
    private String position;
    private String skillNeededList;
    private String jobRequirement;
    private String jobDescription;
    private String jobBenefits;
    private String link;
    private Integer salary;
    private Enum workType;
    private JobLocationDTO jobLocation;
    private String zip;
    private byte isActive;
}
