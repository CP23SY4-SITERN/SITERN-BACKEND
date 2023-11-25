package com.example.siternbackend.entities.internship;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "internshipapplicationstatus_name", schema = "sitern")
public class InternshipapplicationstatusName {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "status_name", nullable = false, length = 45)
    private String statusName;

    @OneToMany(mappedBy = "internshipapplicationstatusName")
    private Set<Internshipapplicationstatus> internshipapplicationstatuses = new LinkedHashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Set<Internshipapplicationstatus> getInternshipapplicationstatuses() {
        return internshipapplicationstatuses;
    }

    public void setInternshipapplicationstatuses(Set<Internshipapplicationstatus> internshipapplicationstatuses) {
        this.internshipapplicationstatuses = internshipapplicationstatuses;
    }

}