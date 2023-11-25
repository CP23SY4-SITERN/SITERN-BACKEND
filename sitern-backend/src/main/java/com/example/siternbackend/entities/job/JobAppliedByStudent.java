package com.example.siternbackend.entities.job;

import com.example.siternbackend.entities.student.StudentProfile;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "job_applied_by_student", schema = "sitern")
public class JobAppliedByStudent {
    @EmbeddedId
    private JobAppliedByStudentId id;

    @MapsId("studentProfileId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_profile_ID", nullable = false, referencedColumnName = "ID")
    private StudentProfile studentProfile;

    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "job_post_ID", referencedColumnName = "ID", nullable = false),
            @JoinColumn(name = "job_post_company_ID", referencedColumnName = "company_ID", nullable = false),
            @JoinColumn(name = "job_post_job_location_ID", referencedColumnName = "job_location_ID", nullable = false)
    })
    private JobPost jobPost;

    @Column(name = "apply_date", nullable = false)
    private Instant applyDate;

    public JobAppliedByStudentId getId() {
        return id;
    }

    public void setId(JobAppliedByStudentId id) {
        this.id = id;
    }

    public StudentProfile getStudentProfile() {
        return studentProfile;
    }

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
    }

    public JobPost getJobPost() {
        return jobPost;
    }

    public void setJobPost(JobPost jobPost) {
        this.jobPost = jobPost;
    }

    public Instant getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Instant applyDate) {
        this.applyDate = applyDate;
    }

}