package com.parkwave.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mall_bookings")
public class MallBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mall_id")
    private Mall mall;

    @ManyToOne
    @JoinColumn(name = "slot_id")
    private MallParkingSlot parkingSlot;

    @Column(name = "booking_date")
    private LocalDate bookingDate;

    @Column(name = "check_in_time")
    private String checkInTime;

    @Column(name = "check_out_time")
    private String checkOutTime;

    @Column(name = "status")
    private String status; // ACTIVE, COMPLETED, CANCELLED

    public MallBooking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Mall getMall() {
        return mall;
    }

    public void setMall(Mall mall) {
        this.mall = mall;
    }

    public MallParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(MallParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
