package com.example.siternbackend.jobs.entities;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "job_location", schema = "sitern")
public class JobLocation {
    @Id
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Set<JobPost> getJobPosts() {
        return jobPosts;
    }

    public void setJobPosts(Set<JobPost> jobPosts) {
        this.jobPosts = jobPosts;
    }

}