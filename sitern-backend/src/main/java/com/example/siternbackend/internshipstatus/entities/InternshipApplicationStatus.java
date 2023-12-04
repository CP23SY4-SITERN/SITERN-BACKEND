package com.example.siternbackend.internshipstatus.entities;

import com.example.siternbackend.company.entities.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "InternshipApplicationStatus", schema = "SITern")
public class InternshipApplicationStatus {
    @EmbeddedId
    private InternshipApplicationStatusId id;

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

}