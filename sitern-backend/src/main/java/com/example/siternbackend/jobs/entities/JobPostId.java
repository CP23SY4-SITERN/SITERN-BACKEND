package com.example.siternbackend.jobs.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JobPostId implements Serializable {
    private static final long serialVersionUID = -5100026122854741217L;
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "company_ID", nullable = false)
    private Integer companyId;

    @Column(name = "job_location_ID", nullable = false)
    private Integer jobLocationId;

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

    public Integer getJobLocationId() {
        return jobLocationId;
    }

    public void setJobLocationId(Integer jobLocationId) {
        this.jobLocationId = jobLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JobPostId entity = (JobPostId) o;
        return Objects.equals(this.jobLocationId, entity.jobLocationId) &&
                Objects.equals(this.companyId, entity.companyId) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobLocationId, companyId, id);
    }

}