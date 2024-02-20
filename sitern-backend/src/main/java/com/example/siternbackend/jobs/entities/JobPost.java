package com.example.siternbackend.jobs.entities;

import com.example.siternbackend.company.entities.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;


    private LocalDate applicationDeadline;


    private String position;


    private String skillNeededList;


    private String jobRequirement;


    private String jobDescription;


    private String jobBenefits;


    private String link;


    private Integer salary;

    private byte isActive = 1;

    @ManyToOne(fetch = FetchType.EAGER, cascade =CascadeType.PERSIST)
    private Company company;


    @Enumerated(EnumType.STRING)
    private WorkType workType;


    @ManyToOne(cascade = CascadeType.ALL)
    private JobLocation jobLocation;


    @OneToMany(mappedBy = "jobPost")
    private Set<JobAppliedByStudent> jobAppliedByStudents = new LinkedHashSet<>();

    @PrePersist
    protected void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
        System.out.println("Setting createdDate: " + this.createdDate);
    }

}