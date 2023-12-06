package com.example.siternbackend.company.DTOS;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Integer id;
    private String companyName;
    private String companyDescription;
    private String companyWebsite;
    private byte[] companyLogo;
    private String companyLocation;
    private Integer companyEmployee;

}
