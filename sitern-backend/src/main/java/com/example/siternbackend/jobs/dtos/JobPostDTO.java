package com.example.siternbackend.jobs.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobPostDTO {
    private Integer id;
    private Long company_ID;
    private String companyName;
    private Instant createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String applicationDeadline;
    private String jobRequirement;
    private String jobDescription;
    private String jobBenefits;
    private String link;
    private Integer salary;
    private String position;
    private String skillNeededList;
    private byte isActive;
//    public LocalDate getApplicationDeadline() {
//        return LocalDate.parse(applicationDeadline);
//    }

}
