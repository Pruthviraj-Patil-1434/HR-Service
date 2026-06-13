    package com.microservice.hr_service.service;

    import com.microservice.hr_service.dto.*;
    import com.microservice.hr_service.entity.HrUser;
    import com.microservice.hr_service.exception.CustomException;
    import com.microservice.hr_service.feign.AuthHRClient;
    import com.microservice.hr_service.repository.HrRepo;
    import com.microservice.hr_service.util.MessageConstant;
    import com.microservice.hr_service.util.Utility;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;

    import java.util.Map;
    import java.util.Optional;

    @Service
    public class Hrservice {

        @Autowired
        HrRepo hrRepo;

        @Autowired
        BCryptPasswordEncoder passwordEncoder;

        @Autowired
        Utility util;

        @Autowired
        AuthHRClient authHRClient;

        public RegisterResponseDTO registerHr(HrDTO hrDTO) {

            String email = hrDTO.getEmail();
            System.out.println("Received HR registration request for email: " + email);
            if (hrRepo.existsByEmail(email)) {
                throw new CustomException(
                        MessageConstant.USER_ALREADY_EXISTS_WITH_EMAIL,
                        HttpStatus.BAD_REQUEST,
                        "USER_ALREADY_EXISTS"
                );
            }

            HrUser hrUser= new HrUser();

            hrUser.setCompanyName(hrDTO.getCompanyName());
            hrUser.setHrName(hrDTO.getHrName());
            hrUser.setEmail(hrDTO.getEmail());
            hrUser.setMobile(hrDTO.getMobile());

            hrUser.setPassword(passwordEncoder.encode(hrDTO.getPassword()));
            hrUser.setHrCode(util.generateHrCode());

            hrUser.setDesignation(hrDTO.getDesignation());
            hrUser.setDepartment(hrDTO.getDepartment());
            hrUser.setAddress(hrDTO.getAddress());
            hrUser.setCountry(hrDTO.getCountry());
            hrUser.setState(hrDTO.getState());
            hrUser.setPostalCode(hrDTO.getPostalCode());

            hrRepo.save(hrUser);

            System.out.println("HR user registered successfully with email: " + email);

            RegisterResponseDTO response = new RegisterResponseDTO("HR registered successfully", hrUser.getHrCode());

            return response;
        }

        public LoginResponseDTO loginHr(HrLoginDTO hrLoginDTO) {

            String email = hrLoginDTO.getEmail();
            String password = hrLoginDTO.getPassword();

            Optional<HrUser> hrUser = Optional.ofNullable(hrRepo.findByEmail(email)
                    .orElseThrow(() -> new CustomException(
                            MessageConstant.USER_NOT_FOUND_WITH_IDENTIFIER,
                            HttpStatus.NOT_FOUND,
                            "USER_NOT_FOUND"
                    )));

            HrUser hrUserEntity = hrUser.get();

            if (  !hrUserEntity.getEmail().equals(email)
                                      ||
                    !passwordEncoder.matches(password, hrUserEntity.getPassword())
                                    ||
                 !hrUserEntity.getHrCode().equals(hrLoginDTO.getHrCode())) {
                throw new CustomException(
                        MessageConstant.INVALID_USERNAME_OR_PASSWORD_OR_HRCODE,
                        HttpStatus.UNAUTHORIZED,
                        "INVALID_CREDENTIALS"
                );
            }

            TokenRequestDTO tokenRequest = new TokenRequestDTO(hrLoginDTO.getEmail(),"ROLE_HR");

            ResponseEntity<Map<String, String>> authResponse = authHRClient.generateHRToken(tokenRequest);

            Map<String, String> responseBody = authResponse.getBody();

            String accessToken= responseBody != null ? responseBody.get("accessToken") : null;
            String refreshToken= responseBody != null ? responseBody.get("refreshToken") : null;

            LoginResponseDTO response = new LoginResponseDTO(accessToken,refreshToken,hrUserEntity.getHrCode(),hrUserEntity.getHrName());
            return response;
        }
    }
