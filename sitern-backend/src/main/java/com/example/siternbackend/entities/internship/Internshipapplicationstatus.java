package com.example.siternbackend.entities.internship;

import com.example.siternbackend.entities.company.Company;
import com.example.siternbackend.entities.student.StudentProfile;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "internshipapplicationstatus", schema = "sitern")
public class Internshipapplicationstatus {
    @EmbeddedId
    private InternshipapplicationstatusId id;

    @MapsId("companyId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "company_ID", nullable = false)
    private Company company;

    @MapsId("internshipapplicationstatusNameId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "InternshipApplicationStatus_name_ID", nullable = false)
    private InternshipapplicationstatusName internshipapplicationstatusName;

    @Column(name = "post_link", nullable = false, length = 45)
    private String postLink;

    @OneToMany(mappedBy = "internshipapplicationstatus")
    private Set<StudentProfile> studentProfiles = new LinkedHashSet<>();

    public InternshipapplicationstatusId getId() {
        return id;
    }

    public void setId(InternshipapplicationstatusId id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public InternshipapplicationstatusName getInternshipapplicationstatusName() {
        return internshipapplicationstatusName;
    }

    public void setInternshipapplicationstatusName(InternshipapplicationstatusName internshipapplicationstatusName) {
        this.internshipapplicationstatusName = internshipapplicationstatusName;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public Set<StudentProfile> getStudentProfiles() {
        return studentProfiles;
    }

    public void setStudentProfiles(Set<StudentProfile> studentProfiles) {
        this.studentProfiles = studentProfiles;
    }

}