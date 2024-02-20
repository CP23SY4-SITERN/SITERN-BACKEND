package com.example.siternbackend.jobs.entities;

import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.internshipstatus.entities.InternshipApplicationStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String road;

    private String subDistrict;


    private String province;


    private String country;


    private String zip;

    @OneToMany(mappedBy = "jobLocation")
    private Set<JobPost> jobPosts = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    private Company company;

}