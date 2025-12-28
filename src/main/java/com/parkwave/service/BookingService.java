package com.parkwave.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkwave.entity.Booking;
import com.parkwave.entity.ParkingSlot;
import com.parkwave.entity.User;
import com.parkwave.repository.BookingRepository;
import com.parkwave.repository.ParkingSlotRepository;
import com.parkwave.repository.UserRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Autowired
    private UserRepository userRepository;

    public Booking saveBooking(Booking booking, int userId) {
        if (booking == null || booking.getParkingSlot() == null) {
            return null;
        }
        
        ParkingSlot slot = parkingSlotRepository.findById(booking.getParkingSlot().getId()).orElse(null);
        User user = userRepository.findById(userId).orElse(null);
        
        if (slot != null && user != null && "AVAILABLE".equals(slot.getStatus())) {
            slot.setStatus("BOOKED");
            parkingSlotRepository.save(slot);
            
            booking.setBookingDate(LocalDate.now());
            booking.setParkingSlot(slot);
            booking.setUser(user);
            return bookingRepository.save(booking);
        }
        return null;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Booking getBookingById(int id) {
        return bookingRepository.findById(id).orElse(null);
    }

    public List<Booking> getBookingsByUserId(int userId) {
        if (userId <= 0) {
            return List.of();
        }
        return bookingRepository.findAll().stream()
            .filter(booking -> booking.getUser() != null && booking.getUser().getId() == userId)
            .toList();
    }

    public void deleteBooking(int id) {
        Booking booking = bookingRepository.findById(id).orElse(null);
        if (booking != null) {
            ParkingSlot slot = booking.getParkingSlot();
            if (slot != null) {
                slot.setStatus("AVAILABLE");
                parkingSlotRepository.save(slot);
            }
            bookingRepository.deleteById(id);
        }
    }
}
