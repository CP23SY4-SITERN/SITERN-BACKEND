package com.example.siternbackend.internshipstatus.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class InternshipApplicationStatusId implements Serializable {
    private static final long serialVersionUID = 8618539372661640789L;
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "company_ID", nullable = false)
    private Integer companyId;

    @Column(name = "InternshipApplicationStatus_name_ID", nullable = false)
    private Integer internshipapplicationstatusNameId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InternshipApplicationStatusId entity = (InternshipApplicationStatusId) o;
        return Objects.equals(this.companyId, entity.companyId) &&
                Objects.equals(this.internshipapplicationstatusNameId, entity.internshipapplicationstatusNameId) &&
                Objects.equals(this.id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, internshipapplicationstatusNameId, id);
    }

}