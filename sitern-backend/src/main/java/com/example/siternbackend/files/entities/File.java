package com.example.siternbackend.files.entities;


import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.jobs.entities.JobLocation;
import com.example.siternbackend.user.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String fileName;

    @NotNull
    private String filePath;

    // Add uploadedDate field with timestamp
    private Date uploadedDate;

    // Add status field to indicate the status of the file
    private String status;

    private String comment;

    @JsonBackReference
    @ManyToOne
    private User user;

}
