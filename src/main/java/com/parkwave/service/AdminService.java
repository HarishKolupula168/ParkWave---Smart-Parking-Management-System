package com.parkwave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parkwave.entity.Admin;
import com.parkwave.repository.AdminRepository;
import com.parkwave.repository.MallParkingSlotRepository;
import com.parkwave.repository.MallBookingRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MallParkingSlotRepository mallParkingSlotRepository;

    @Autowired
    private MallBookingRepository mallBookingRepository;

    public Admin authenticate(String username, String password) {
        Admin admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null && admin.getPassword().equals(password) && admin.isActive()) {
            return admin;
        }
        return null;
    }

    public String getMallStatistics(int mallId) {
        long totalSlots = mallParkingSlotRepository.countByMallId(mallId);
        long availableSlots = mallParkingSlotRepository.countAvailableSlotsByMall(mallId);
        long bookedSlots = totalSlots - availableSlots;
        long totalBookings = mallBookingRepository.countByMallId(mallId);
        long activeBookings = mallBookingRepository.countActiveBookingsByMall(mallId);

        return String.format(
            "Mall Stats - Total Slots: %d, Available: %d, Booked: %d, Total Bookings: %d, Active: %d",
            totalSlots, availableSlots, bookedSlots, totalBookings, activeBookings
        );
    }

    public Admin createAdmin(Admin admin) {
        if (adminRepository.existsByUsername(admin.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (adminRepository.existsByEmail(admin.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        return adminRepository.save(admin);
    }
}
