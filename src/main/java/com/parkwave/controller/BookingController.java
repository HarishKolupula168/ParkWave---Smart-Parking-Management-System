package com.parkwave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.parkwave.entity.Booking;
import com.parkwave.service.BookingService;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin
public class BookingController {

    @Autowired
    private BookingService bookingService;

    // Create booking
    @PostMapping
    public Booking createBooking(@RequestBody Booking booking, @RequestParam int userId) {
        Booking result = bookingService.saveBooking(booking, userId);
        if (result == null) {
            throw new RuntimeException("Slot is not available for booking");
        }
        return result;
    }

    // Get all bookings
    @GetMapping
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // Get booking by ID
    @GetMapping("/{id}")
    public Booking getBookingById(@PathVariable int id) {
        return bookingService.getBookingById(id);
    }

    // Get bookings by user ID
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUserId(@PathVariable int userId) {
        return bookingService.getBookingsByUserId(userId);
    }

    // Delete booking (cancel)
    @DeleteMapping("/{id}")
    public String deleteBooking(@PathVariable int id) {
        bookingService.deleteBooking(id);
        return "Booking cancelled successfully";
    }
}
