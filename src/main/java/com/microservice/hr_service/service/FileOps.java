package com.microservice.hr_service.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileOps {


    private final String PROFILE_FOLDER= "C:/Users/PRUTHIVIRAJ/Documents/CRM-PROJECT/hr-service/uploads/profile_photo/";


    public String saveLogo(MultipartFile file) throws IOException {
        String filepath = PROFILE_FOLDER + file.getOriginalFilename();
        file.transferTo(new java.io.File(filepath));
        return filepath;
    }

}
