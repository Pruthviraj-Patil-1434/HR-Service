package com.microservice.hr_service.controller;

import com.microservice.hr_service.dto.*;
import com.microservice.hr_service.exception.CustomException;
import com.microservice.hr_service.service.Hrservice;
import com.microservice.hr_service.util.MessageConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/hr")
public class HrController {

    @Autowired
    Hrservice hrservice;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> saveClinet(
            @RequestBody HrDTO hrDTO) {

        try {

            RegisterResponseDTO response =
                    hrservice.registerHr(hrDTO);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(response);

        }catch (CustomException e) {

            RegisterResponseDTO errorResponse = new RegisterResponseDTO(
                    e.getMessage(),
                    null);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errorResponse);

        } catch (Exception e) {

            RegisterResponseDTO errorResponse=new RegisterResponseDTO(
                    "Client Registration Failed",
                    null);

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginClient(
            @RequestBody HrLoginDTO hrLoginDTO) {

        try {


            LoginResponseDTO response =
                    hrservice.loginHr(hrLoginDTO);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(response);

        } catch (CustomException e) {

            ApiResponse<Map<String, String>> errorResponse = ApiResponse.error(
                    e.getMessage(),
                    e.getStatus().value()
            );

            return ResponseEntity.status(e.getStatus()).body(errorResponse);

        } catch (Exception e) {

            ApiResponse<Map<String, String>> errorResponse = ApiResponse.error(
                    MessageConstant.INVALID_USERNAME_OR_PASSWORD_OR_HRCODE + e.getMessage(),
                    HttpStatus.UNAUTHORIZED.value()
            );

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(errorResponse);
        }
    }
}
