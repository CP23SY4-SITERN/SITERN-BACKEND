package com.example.siternbackend.jobs.entities;

import com.example.siternbackend.company.entities.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "job_post", schema = "sitern")
public class JobPost {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @JsonIgnore
    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "applicationDeadline")
    @JsonIgnore
    private LocalDate applicationDeadline;

    @Column(name = "position", nullable = false, length = 1000)
    private String position;

    @Column(name = "skill_needed_list", nullable = false, length = 2000)
    private String skillNeededList;

    @Column(name = "job_requirement", nullable = false, length = 2000)
    private String jobRequirement;

    @Column(name = "job_description", length = 10000)
    private String jobDescription;

    @Column(name = "job_benefits", length = 5000)
    private String jobBenefits;

    @Column(name = "link", length = 300)
    private String link;

    @JsonIgnore
    @Column(name = "salary", nullable = false)
    private Integer salary;

    @JsonIgnore
    @Column(name = "is_active")
    private byte isActive;

    @MapsId("companyId")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade =CascadeType.PERSIST)
    @JoinColumn(name = "company_ID", nullable = false)
    private Company company;

    @Column(name = "company_ID")
    @JsonIgnore
    private Long company_ID;

    @Enumerated(EnumType.STRING)
    @Column(name = "work_type", nullable = false, length = 10)
    private WorkType workType;


    public enum WorkType {
        Hybrid,
        Remote,
        Onsite
    }

    @MapsId("jobLocationId")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "job_location_ID", nullable = false)
    private JobLocation jobLocation;

    @Column(name = "job_location_ID")
    @JsonIgnore
    private Long job_location_ID;




    @OneToMany(mappedBy = "jobPost")
    @JsonIgnore
    private Set<JobAppliedByStudent> jobAppliedByStudents = new LinkedHashSet<>();



}