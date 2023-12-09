package com.example.siternbackend.company.entities;

import com.example.siternbackend.internshipstatus.entities.InternshipApplicationStatus;
import com.example.siternbackend.jobs.entities.JobPost;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "company", schema = "sitern")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "company_description", length = 1000)
    private String companyDescription;

    @Column(name = "company_website", length = 200)
    private String companyWebsite;

    @Column(name = "company_logo")
    private byte[] companyLogo;

    @Column(name = "company_location", length = 200)
    private String companyLocation;

    @Column(name = "company_employee")
    private Integer companyEmployee;



    @OneToMany(mappedBy = "company")
    private Set<InternshipApplicationStatus> internshipapplicationstatuses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<JobPost> jobPosts = new LinkedHashSet<>();


}