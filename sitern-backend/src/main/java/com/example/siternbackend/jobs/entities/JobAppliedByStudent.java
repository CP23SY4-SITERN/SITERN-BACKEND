package com.example.siternbackend.jobs.entities;

//import com.example.siternbackend.student.entities.StudentProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobAppliedByStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    private StudentProfile studentProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private JobPost jobPost;

    private Instant applyDate;

}