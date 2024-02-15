package com.example.siternbackend.user.repositories;

import com.example.siternbackend.user.entities.Authorities;
import com.example.siternbackend.user.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Integer> {

    Authorities findByRoles(Roles roles);

    List<Authorities> findByUserId(int userId);
}
