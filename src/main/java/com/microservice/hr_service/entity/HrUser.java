package com.microservice.hr_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "hr_users", schema = "startup_crm")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hr_code", unique = true, nullable = false, length = 10)
    private String hrCode;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "hr_name", nullable = false)
    private String hrName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "mobile", nullable = false, length = 20)
    private String mobile;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "country", length = 100)
    private String country;

    @Column(name = "state", length = 100)
    private String state;

    @Column(name = "postal_code", length = 20)
    private String postalCode;

    @Column(name = "profile_photo", length = 500)
    private String profilePhoto;

    @Column(name = "auth_user_id")
    private Long authUserId;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        isActive = true;
    }

}