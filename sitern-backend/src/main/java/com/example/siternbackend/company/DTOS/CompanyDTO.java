package com.example.siternbackend.company.DTOS;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Integer id;
    @NotNull
    @Size(min=1,max = 100 , message = "Company Name must be between 1 to 100 characters")
    private String companyName;
    @Size(max = 1000 , message = "Company Description must lower than 1000 characters")
    private String companyDescription;
    @Size(max = 200 , message = "Company Website must lower than 200 characters")
    private String companyWebsite;
    private byte[] companyLogo;
    @Size(max = 200 , message = "Company Location must lower than 200 characters")
    private String companyLocation;
    private Integer companyEmployee;

}
