package com.parkwave.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.parkwave.entity.MallBooking;
import com.parkwave.service.MallBookingService;

@RestController
@RequestMapping("/api/mall-bookings")
@CrossOrigin
public class MallBookingController {

    @Autowired
    private MallBookingService mallBookingService;

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestParam int userId, @RequestParam int mallId, @RequestParam int slotId) {
        MallBooking result = mallBookingService.createBooking(userId, mallId, slotId);
        if (result == null) {
            return ResponseEntity.badRequest().body("Booking failed - slot may not be available");
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}")
    public List<MallBooking> getUserBookings(@PathVariable int userId) {
        return mallBookingService.getUserBookings(userId);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable int bookingId) {
        MallBooking booking = mallBookingService.getBookingById(bookingId);
        if (booking == null) {
            return ResponseEntity.status(404).body("Booking not found");
        }
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/user/{userId}/active")
    public List<MallBooking> getActiveUserBookings(@PathVariable int userId) {
        return mallBookingService.getActiveUserBookings(userId);
    }

    @GetMapping("/mall/{mallId}")
    public List<MallBooking> getMallBookings(@PathVariable int mallId) {
        return mallBookingService.getMallBookings(mallId);
    }

    @PostMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable int bookingId) {
        mallBookingService.cancelBooking(bookingId);
        return "Booking cancelled successfully";
    }

    @PostMapping("/guest")
    public ResponseEntity<?> createGuestBooking(@RequestParam String guestName, @RequestParam int mallId, @RequestParam int slotId) {
        MallBooking result = mallBookingService.createGuestBooking(guestName, mallId, slotId);
        if (result == null) {
            return ResponseEntity.badRequest().body("Guest booking failed - slot may not be available");
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/complete/{bookingId}")
    public String completeBooking(@PathVariable int bookingId) {
        mallBookingService.completeBooking(bookingId);
        return "Booking completed successfully";
    }
}
