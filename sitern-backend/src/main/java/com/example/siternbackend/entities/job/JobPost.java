package com.example.siternbackend.entities.job;


import com.example.siternbackend.entities.company.Company;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "job_post", schema = "SITern")
public class JobPost {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @MapsId("companyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_ID", nullable = false)
    private Company company;

    @MapsId("jobLocationId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_location_ID", nullable = false)
    private JobLocation jobLocation;

    @Column(name = "created_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private Instant createdDate;

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

    @Column(name = "salary", nullable = false)
    private Byte salary;

    @Column(name = "is_active")
    private Byte isActive;

    @OneToMany(mappedBy = "jobPost")
    private Set<JobAppliedByStudent> jobAppliedByStudents = new LinkedHashSet<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public JobLocation getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(JobLocation jobLocation) {
        this.jobLocation = jobLocation;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSkillNeededList() {
        return skillNeededList;
    }

    public void setSkillNeededList(String skillNeededList) {
        this.skillNeededList = skillNeededList;
    }

    public String getJobRequirement() {
        return jobRequirement;
    }

    public void setJobRequirement(String jobRequirement) {
        this.jobRequirement = jobRequirement;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobBenefits() {
        return jobBenefits;
    }

    public void setJobBenefits(String jobBenefits) {
        this.jobBenefits = jobBenefits;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Byte getSalary() {
        return salary;
    }

    public void setSalary(Byte salary) {
        this.salary = salary;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Set<JobAppliedByStudent> getJobAppliedByStudents() {
        return jobAppliedByStudents;
    }

    public void setJobAppliedByStudents(Set<JobAppliedByStudent> jobAppliedByStudents) {
        this.jobAppliedByStudents = jobAppliedByStudents;
    }

}