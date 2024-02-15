package com.example.siternbackend.jobs.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;
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
    private Set<JobPost> jobPosts = new LinkedHashSet<>();


}