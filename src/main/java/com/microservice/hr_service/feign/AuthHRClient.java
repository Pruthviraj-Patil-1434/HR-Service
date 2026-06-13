package com.microservice.hr_service.feign;

import com.microservice.hr_service.dto.TokenRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "STARTUP-AUTHENTICATION-SERVICE")
public interface AuthHRClient {

    @PostMapping("/auth/generate-hr-token")
    public ResponseEntity<Map<String,String>> generateHRToken(@RequestBody TokenRequestDTO tokenRequest);
}
