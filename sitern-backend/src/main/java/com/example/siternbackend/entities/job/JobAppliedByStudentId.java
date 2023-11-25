package com.example.siternbackend.entities.job;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class JobAppliedByStudentId implements Serializable {
    private static final long serialVersionUID = 7810743170125039876L;
    @Column(name = "student_profile_ID", nullable = false)
    private Integer studentProfileId;

    @Column(name = "job_post_ID", nullable = false)
    private Integer jobPostId;

    @Column(name = "job_post_company_ID", nullable = false)
    private Integer jobPostCompanyId;

    @Column(name = "job_post_job_location_ID", nullable = false)
    private Integer jobPostJobLocationId;

    public Integer getStudentProfileId() {
        return studentProfileId;
    }

    public void setStudentProfileId(Integer studentProfileId) {
        this.studentProfileId = studentProfileId;
    }

    public Integer getJobPostId() {
        return jobPostId;
    }

    public void setJobPostId(Integer jobPostId) {
        this.jobPostId = jobPostId;
    }

    public Integer getJobPostCompanyId() {
        return jobPostCompanyId;
    }

    public void setJobPostCompanyId(Integer jobPostCompanyId) {
        this.jobPostCompanyId = jobPostCompanyId;
    }

    public Integer getJobPostJobLocationId() {
        return jobPostJobLocationId;
    }

    public void setJobPostJobLocationId(Integer jobPostJobLocationId) {
        this.jobPostJobLocationId = jobPostJobLocationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        JobAppliedByStudentId entity = (JobAppliedByStudentId) o;
        return Objects.equals(this.jobPostCompanyId, entity.jobPostCompanyId) &&
                Objects.equals(this.jobPostId, entity.jobPostId) &&
                Objects.equals(this.jobPostJobLocationId, entity.jobPostJobLocationId) &&
                Objects.equals(this.studentProfileId, entity.studentProfileId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobPostCompanyId, jobPostId, jobPostJobLocationId, studentProfileId);
    }

}