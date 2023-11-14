package com.example.siternbackend.internshipstatus.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class InternshipapplicationstatusId implements Serializable {
    private static final long serialVersionUID = 5457430311440027283L;
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "company_ID", nullable = false)
    private Integer companyId;

    @Column(name = "InternshipApplicationStatus_name_ID", nullable = false)
    private Integer internshipapplicationstatusNameId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getInternshipapplicationstatusNameId() {
        return internshipapplicationstatusNameId;
    }

    public void setInternshipapplicationstatusNameId(Integer internshipapplicationstatusNameId) {
        this.internshipapplicationstatusNameId = internshipapplicationstatusNameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InternshipapplicationstatusId entity = (InternshipapplicationstatusId) o;
        return Objects.equals(this.companyId, entity.companyId) &&
                Objects.equals(this.internshipapplicationstatusNameId, entity.internshipapplicationstatusNameId) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, internshipapplicationstatusNameId, id);
    }

}