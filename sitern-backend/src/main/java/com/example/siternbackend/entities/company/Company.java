package com.example.siternbackend.entities.company;

import com.example.siternbackend.entities.internship.Internshipapplicationstatus;
import com.example.siternbackend.entities.job.JobPost;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "company", schema = "sitern")
public class Company {
    @Id
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
    private Set<Internshipapplicationstatus> internshipapplicationstatuses = new LinkedHashSet<>();

    @OneToMany(mappedBy = "company")
    private Set<JobPost> jobPosts = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public byte[] getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(byte[] companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public Integer getCompanyEmployee() {
        return companyEmployee;
    }

    public void setCompanyEmployee(Integer companyEmployee) {
        this.companyEmployee = companyEmployee;
    }

    public Set<Internshipapplicationstatus> getInternshipapplicationstatuses() {
        return internshipapplicationstatuses;
    }

    public void setInternshipapplicationstatuses(Set<Internshipapplicationstatus> internshipapplicationstatuses) {
        this.internshipapplicationstatuses = internshipapplicationstatuses;
    }

    public Set<JobPost> getJobPosts() {
        return jobPosts;
    }

    public void setJobPosts(Set<JobPost> jobPosts) {
        this.jobPosts = jobPosts;
    }

}