package com.example.siternbackend.jobs.dtos;

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
    private String road;
    private String subDistrict;
    private String province;
    private String country;
    private String zip;
}
