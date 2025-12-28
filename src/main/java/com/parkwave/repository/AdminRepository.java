package com.parkwave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parkwave.entity.Admin;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByUsername(String username);
    Optional<Admin> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
