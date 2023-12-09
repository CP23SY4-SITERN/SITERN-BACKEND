package com.example.siternbackend.jobs.entities;

import com.example.siternbackend.company.entities.Company;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "job_post", schema = "sitern")
public class JobPost {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @MapsId("companyId")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "job_location_ID", nullable = false)
    private JobLocation jobLocation;

    @JsonIgnore
    @Column(name = "created_date", nullable = false)
    private Instant createdDate;

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
    private int salary;
    @JsonIgnore
    @Column(name = "is_active")
    private byte isActive;

    @OneToMany(mappedBy = "jobPost")
    @JsonIgnore
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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
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