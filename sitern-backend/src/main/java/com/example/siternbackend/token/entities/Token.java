package com.example.siternbackend.token.entities;

import com.example.siternbackend.user.entities.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String token;

    Boolean isRevoke;

    Boolean isAccess;

    Date expiration;

    @ManyToOne
    User user;


}
