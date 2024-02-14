package com.example.siternbackend.jobs.dtos;

import com.example.siternbackend.Validation.EnumValid;
import com.example.siternbackend.jobs.entities.WorkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreatingJobDTO {
    private Integer id;
    @NotNull(message = "must not be null")
    @Size(min=1,max = 200 , message = "Job Title must be between 1 to 200 characters")
    private String title;
    @JsonIgnore
    private LocalDateTime createdDate;
    @NotNull(message = "must not be null")
    private Integer company_ID;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationDeadline;
    @NotNull(message = "must not be null")
    @Size(min=1,max = 1000 , message = "Job Position must be between 1 to 1000 characters")
    private String position;
    @NotNull(message = "must not be null")
    @Size(min=1,max = 2000 , message = "Job Skill must be between 1 to 2000 characters")
    private String skillNeededList;
    @NotNull(message = "must not be null")
    @Size(min=1,max = 2000 , message = "Job Requirement must be between 1 to 2000 characters")
    private String jobRequirement;
    @Size(max = 10000 , message = "Job Description must lower than 10000 characters")
    private String jobDescription;
    @Size(max = 5000 , message = "Job Benefits must lower than 5000 characters")
    private String jobBenefits;
    @Size(max = 300 , message = "Job Link must lower than 300 characters")
    private String link;
    @NotNull(message = "must not be null")
    private Integer salary;
    @EnumValid(enumClass = WorkType.class, message = "Invalid workType value. Accepted values are: {enumValues}")
    @Enumerated(EnumType.STRING)
    private WorkType workType;
    @NotNull(message = "must not be null")
    private Integer job_location_ID;
    private byte isActive;

}
