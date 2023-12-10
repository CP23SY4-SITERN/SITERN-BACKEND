package com.example.siternbackend.jobs.entities;

import com.example.siternbackend.company.entities.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
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


    @Column(name = "created_date", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createdDate;

    @Column(name = "applicationDeadline")
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

    @Column(name = "is_active")
    private byte isActive = 1;

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

    @PrePersist
    protected void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
        System.out.println("Setting createdDate: " + this.createdDate);
    }

}