package com.parkwave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.parkwave.entity.Admin;
import com.parkwave.repository.AdminRepository;

@Component
@Order(2) // Run after mall data initialization
public class AdminDataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public void run(String... args) throws Exception {
        if (adminRepository.count() == 0) {
            createDefaultAdmin();
        }
    }

    private void createDefaultAdmin() {
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPassword("admin123"); // In production, use encrypted passwords
        admin.setEmail("admin@parkwave.com");
        admin.setFullName("System Administrator");
        
        adminRepository.save(admin);
        System.out.println("Default admin account created: username=admin, password=admin123");
    }
}
