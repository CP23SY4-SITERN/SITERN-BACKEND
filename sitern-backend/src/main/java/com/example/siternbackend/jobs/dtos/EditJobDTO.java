package com.example.siternbackend.jobs.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditJobDTO {
    private Integer id;
    @NotNull(message = "must not be null")
    @Size(min=1,max = 200 , message = "Job Title must be between 1 to 200 characters")
    private String title;
    @Future(message = "deadline must be future")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String applicationDeadline;
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
    @NotNull(message = "must not be null")
    @Enumerated
    private WorkType workType;
    @NotNull(message = "must not be null")
    private Integer job_location_ID;
    @JsonIgnore
    private byte isActive;

    public enum WorkType {
        Hybrid,
        Remote,
        Onsite
    }
}
