package com.microservice.hr_service.repository;

import com.microservice.hr_service.entity.HrUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HrRepo extends JpaRepository<HrUser,Long> {

    public boolean existsByEmail(String email);

    public Optional<HrUser> findByEmail(String email);
}
