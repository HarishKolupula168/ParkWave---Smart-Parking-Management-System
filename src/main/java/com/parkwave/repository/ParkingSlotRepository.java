package com.parkwave.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.parkwave.entity.ParkingSlot;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Integer> {
}
