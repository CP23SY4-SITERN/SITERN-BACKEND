package com.example.siternbackend.student.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class StudentProfileId implements Serializable {
    private static final long serialVersionUID = 5252316673442995315L;
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "user_ID", nullable = false)
    private Integer userId;

    @Column(name = "InternshipApplicationStatus_ID", nullable = false)
    private Integer internshipapplicationstatusId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getInternshipapplicationstatusId() {
        return internshipapplicationstatusId;
    }

    public void setInternshipapplicationstatusId(Integer internshipapplicationstatusId) {
        this.internshipapplicationstatusId = internshipapplicationstatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        StudentProfileId entity = (StudentProfileId) o;
        return Objects.equals(this.internshipapplicationstatusId, entity.internshipapplicationstatusId) &&
                Objects.equals(this.id, entity.id) &&
                Objects.equals(this.userId, entity.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internshipapplicationstatusId, id, userId);
    }

}