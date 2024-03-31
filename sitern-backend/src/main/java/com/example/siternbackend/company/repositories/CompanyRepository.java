package com.example.siternbackend.company.repositories;

import com.example.siternbackend.company.entities.Company;
import com.example.siternbackend.jobs.entities.JobLocation;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
//    Optional<Company> findByIdWithJobLocations(Integer companyId);
    @Query("SELECT c FROM Company c JOIN FETCH c.jobLocations WHERE c.id = :company_id")
    Optional<Company> findByIdWithJobLocations(@Param("company_id") Integer company_id);

    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.jobLocations")
    List<Company> findAllWithJobLocations();
//    @Query("SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.jobLocations WHERE c.id = :companyId")
//    Optional<Company> findByIdWithJobLocations(@Param("companyId") Integer companyId);
//    @Query("SELECT c FROM Company c LEFT JOIN FETCH c.jobLocations WHERE c.id = :companyId")
//    Optional<Company> findByIdWithJobLocations(@Param("companyId") Integer companyId);

}