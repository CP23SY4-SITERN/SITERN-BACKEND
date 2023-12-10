package com.example.siternbackend.jobs.dtos;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JobLocationDTO {

    private Integer id;
    @Size(max = 45 , message = "Road must lower than 45 characters")
    private String road;
    @Size(max = 45 , message = "Sub District must lower than 45 characters")
    private String subDistrict;
    @Size(max = 45 , message = "Province must lower than 45 characters")
    private String province;
    @Size(max = 45 , message = "Country must lower than 45 characters")
    //The longest country name in terms of characters is the United Kingdom of Great Britain and Northern Ireland. The name consists of 56 characters.
    private String country;
    @Size(max = 45 , message = "Zip must lower than 45 characters")
    private String zip;
}
