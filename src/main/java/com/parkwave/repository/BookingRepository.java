package com.parkwave.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkwave.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
}
