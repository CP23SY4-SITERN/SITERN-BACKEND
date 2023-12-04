package com.example.siternbackend.internshipstatus.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "InternshipApplicationStatus_name", schema = "SITern")
public class InternshipapplicationstatusName {
    @Id
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "status_name", nullable = false, length = 45)
    private String statusName;

}