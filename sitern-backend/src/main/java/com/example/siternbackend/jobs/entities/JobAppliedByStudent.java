package com.example.siternbackend.jobs.entities;

import com.example.siternbackend.student.entities.StudentProfile;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "job_applied_by_student", schema = "SITern")
public class JobAppliedByStudent {
    @EmbeddedId
    private JobAppliedByStudentId id;

    @MapsId("studentProfileId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_profile_ID", nullable = false, referencedColumnName = "ID")
    private StudentProfile studentProfile;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private JobPost jobPost;

    @Column(name = "apply_date", nullable = false)
    private Instant applyDate;

}