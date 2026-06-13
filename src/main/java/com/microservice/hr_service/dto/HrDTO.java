package com.microservice.hr_service.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrDTO {

    private String companyName;

    private String hrName;

    private String email;

    private String mobile;

    private String password;

    private String designation;

    private String department;

    private String address;

    private String country;

    private String state;

    private String postalCode;
}