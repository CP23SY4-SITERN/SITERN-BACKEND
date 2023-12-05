package com.example.siternbackend.jobs.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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
//    private Long companyId;
//    private String companyName;
//    private Long jobLocationId;
//    private String jobLocationName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant createdDate;
    
    private LocalDate applicationDeadline;
    private String position;
    private String skillNeededList;
    private String jobRequirement;
    private String jobDescription;
    private String jobBenefits;
    private String link;
    private Byte salary;
    private Byte isActive;

    // Constructors, getters, and setters...


}

