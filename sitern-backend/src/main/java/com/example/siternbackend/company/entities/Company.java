package com.example.siternbackend.company.entities;

import com.example.siternbackend.internshipstatus.entities.InternshipApplicationStatus;
import com.example.siternbackend.jobs.dtos.JobLocationDTO;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.jobs.entities.JobPost;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String companyName;

    private String companyDescription;

    private String companyWebsite;

    private byte[] companyLogo;


    private String companyLocation;


    private Integer companyEmployee;



    @OneToMany(mappedBy = "company")
    private Set<InternshipApplicationStatus> internshipapplicationstatuses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<JobPost> jobPosts = new LinkedHashSet<>();

//    @ManyToOne
//    private JobLocation jobLocation;
    @JsonManagedReference
    @OneToMany(mappedBy = "company")
    private Set<JobLocation> jobLocations = new HashSet<>();

//    @MapsId("companyId")
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.EAGER, cascade =CascadeType.PERSIST)
//    @JoinColumn(name = "company_ID", nullable = false)
//    private Company company;
//
//    @Column(name = "company_ID")
//    @JsonIgnore
//    private Long company_ID;



}