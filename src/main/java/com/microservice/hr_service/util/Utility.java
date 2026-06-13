package com.microservice.hr_service.util;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class Utility {


    public String generateHrCode() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return "HR" + number;
    }
    
}


