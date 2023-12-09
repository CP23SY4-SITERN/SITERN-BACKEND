package com.example.siternbackend.jobs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
@Getter
@Setter
@Entity
@Table(name = "job_location", schema = "sitern")
public class JobLocation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "road", length = 45)
    private String road;

    @Column(name = "sub_district", length = 45)
    private String subDistrict;

    @Column(name = "province", length = 45)
    private String province;

    @Column(name = "country", length = 45)
    private String country;

    @Column(name = "zip", length = 45)
    private String zip;

    @OneToMany(mappedBy = "jobLocation")
    private Set<JobPost> jobPosts = new LinkedHashSet<>();


}