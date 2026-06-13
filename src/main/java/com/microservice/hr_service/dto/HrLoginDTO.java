package com.microservice.hr_service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HrLoginDTO {

    private String email;
    private String password;
    private String hrCode;

}
