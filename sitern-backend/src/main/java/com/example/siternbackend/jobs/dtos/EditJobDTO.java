package com.example.siternbackend.jobs.dtos;

import com.example.siternbackend.jobs.entities.WorkType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditJobDTO {
    private Integer id;
    @Size(min=1,max = 200 , message = "Job Title must be between 1 to 200 characters")
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applicationDeadline;
    @Size(min=1,max = 2000 , message = "Job Skill must be between 1 to 2000 characters")
    private String skillNeededList;
    @Size(max = 2000 , message = "Job Requirement must be between 1 to 2000 characters")
    private String jobRequirement;
    @Size(max = 10000 , message = "Job Description must lower than 10000 characters")
    private String jobDescription;
    @Size(max = 5000 , message = "Job Benefits must lower than 5000 characters")
    private String jobBenefits;
    @Size(max = 300 , message = "Job Link must lower than 300 characters")
    private String link;
    private Integer salary;
    @Enumerated(EnumType.STRING)
    private WorkType workType;
    private String contact;
    private Integer job_location_ID;

}
