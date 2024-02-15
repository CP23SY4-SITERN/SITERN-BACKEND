package com.example.siternbackend.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Authorities implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "authorities_seq")
    @SequenceGenerator(name="authorities_seq", sequenceName = "authorities_seq", allocationSize = 1)
    Long id;

    @Enumerated(EnumType.STRING)
    Roles roles;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    List<User> user;
}
