package com.parkwave.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.parkwave.entity.Admin;
import com.parkwave.entity.MallParkingSlot;
import com.parkwave.entity.MallBooking;
import com.parkwave.service.AdminService;
import com.parkwave.service.MallParkingSlotService;
import com.parkwave.service.MallBookingService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private MallParkingSlotService mallParkingSlotService;

    @Autowired
    private MallBookingService mallBookingService;

    // Admin Authentication
    @PostMapping("/login")
    public Admin login(@RequestParam String username, @RequestParam String password) {
        return adminService.authenticate(username, password);
    }

    // Slot Management
    @PostMapping("/slots")
    public MallParkingSlot addSlot(@RequestBody MallParkingSlot slot) {
        return mallParkingSlotService.createSlot(slot);
    }

    @DeleteMapping("/slots/{slotId}")
    public String deleteSlot(@PathVariable int slotId) {
        boolean deleted = mallParkingSlotService.deleteSlot(slotId);
        return deleted ? "Slot deleted successfully" : "Slot not found or cannot be deleted";
    }

    @PutMapping("/slots/{slotId}")
    public MallParkingSlot updateSlot(@PathVariable int slotId, @RequestBody MallParkingSlot slot) {
        return mallParkingSlotService.updateSlot(slotId, slot);
    }

    // Booking Management
    @GetMapping("/bookings")
    public List<MallBooking> getAllBookings() {
        return mallBookingService.getAllBookings();
    }

    @DeleteMapping("/bookings/{bookingId}")
    public String forceCancelBooking(@PathVariable int bookingId) {
        mallBookingService.forceCancelBooking(bookingId);
        return "Booking cancelled by admin";
    }

    @GetMapping("/bookings/mall/{mallId}")
    public List<MallBooking> getBookingsByMall(@PathVariable int mallId) {
        return mallBookingService.getMallBookings(mallId);
    }

    // Mall Statistics
    @GetMapping("/stats/mall/{mallId}")
    public String getMallStats(@PathVariable int mallId) {
        return adminService.getMallStatistics(mallId);
    }
}
