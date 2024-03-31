package com.example.siternbackend.user.services;

import com.example.siternbackend.user.entities.Authorities;
import com.example.siternbackend.user.repositories.AuthoritiesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthoritiesService {
    final AuthoritiesRepository authoritiesRepository;

    public List<Authorities> findAll() {
        return authoritiesRepository.findAll();
    }

    public List<Authorities> findByUserId(int userId) {
        return authoritiesRepository.findByUserId(userId);
    }
}
