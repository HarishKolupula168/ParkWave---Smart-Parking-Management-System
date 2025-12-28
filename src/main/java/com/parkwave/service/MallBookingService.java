package com.parkwave.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parkwave.entity.MallBooking;
import com.parkwave.entity.MallParkingSlot;
import com.parkwave.entity.User;
import com.parkwave.repository.MallBookingRepository;
import com.parkwave.repository.MallParkingSlotRepository;
import com.parkwave.repository.UserRepository;

@Service
public class MallBookingService {

    @Autowired
    private MallBookingRepository mallBookingRepository;

    @Autowired
    private MallParkingSlotRepository mallParkingSlotRepository;

    @Autowired
    private UserRepository userRepository;

    public MallBooking createBooking(int userId, int mallId, int slotId) {
        User user = userRepository.findById(userId).orElse(null);
        MallParkingSlot slot = mallParkingSlotRepository.findById(slotId).orElse(null);
        
        if (user != null && slot != null && "AVAILABLE".equals(slot.getStatus()) && slot.getMall().getId() == mallId) {
            // Book the slot
            slot.setStatus("BOOKED");
            mallParkingSlotRepository.save(slot);
            
            // Create booking
            MallBooking booking = new MallBooking();
            booking.setUser(user);
            booking.setMall(slot.getMall());
            booking.setParkingSlot(slot);
            booking.setBookingDate(LocalDate.now());
            booking.setCheckInTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            booking.setStatus("ACTIVE");
            
            return mallBookingRepository.save(booking);
        }
        return null;
    }

    public List<MallBooking> getUserBookings(int userId) {
        return mallBookingRepository.findByUserId(userId);
    }

    public List<MallBooking> getMallBookings(int mallId) {
        return mallBookingRepository.findByMallId(mallId);
    }

    public List<MallBooking> getActiveUserBookings(int userId) {
        return mallBookingRepository.findByUserIdAndStatus(userId, "ACTIVE");
    }

    public void cancelBooking(int bookingId) {
        MallBooking booking = mallBookingRepository.findById(bookingId).orElse(null);
        if (booking != null && "ACTIVE".equals(booking.getStatus())) {
            // Release the slot
            MallParkingSlot slot = booking.getParkingSlot();
            slot.setStatus("AVAILABLE");
            mallParkingSlotRepository.save(slot);
            
            // Update booking status
            booking.setStatus("CANCELLED");
            booking.setCheckOutTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            mallBookingRepository.save(booking);
        }
    }

    public MallBooking createGuestBooking(String guestName, int mallId, int slotId) {
        MallParkingSlot slot = mallParkingSlotRepository.findById(slotId).orElse(null);
        
        if (slot != null && "AVAILABLE".equals(slot.getStatus()) && slot.getMall().getId() == mallId) {
            // Book the slot
            slot.setStatus("BOOKED");
            mallParkingSlotRepository.save(slot);
            
            // Create booking for guest (user will be null)
            MallBooking booking = new MallBooking();
            booking.setUser(null); // Guest booking - no user
            booking.setMall(slot.getMall());
            booking.setParkingSlot(slot);
            booking.setBookingDate(LocalDate.now());
            booking.setCheckInTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            booking.setStatus("ACTIVE");
            
            return mallBookingRepository.save(booking);
        }
        return null;
    }

    public void completeBooking(int bookingId) {
        MallBooking booking = mallBookingRepository.findById(bookingId).orElse(null);
        if (booking != null && "ACTIVE".equals(booking.getStatus())) {
            // Release the slot
            MallParkingSlot slot = booking.getParkingSlot();
            slot.setStatus("AVAILABLE");
            mallParkingSlotRepository.save(slot);
            
            // Update booking status
            booking.setStatus("COMPLETED");
            booking.setCheckOutTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            mallBookingRepository.save(booking);
        }
    }

    // Admin methods
    public List<MallBooking> getAllBookings() {
        return mallBookingRepository.findAll();
    }

    public void forceCancelBooking(int bookingId) {
        MallBooking booking = mallBookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            // Release the slot regardless of status
            MallParkingSlot slot = booking.getParkingSlot();
            slot.setStatus("AVAILABLE");
            mallParkingSlotRepository.save(slot);
            
            // Update booking status
            booking.setStatus("CANCELLED_BY_ADMIN");
            booking.setCheckOutTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            mallBookingRepository.save(booking);
        }
    }
}
