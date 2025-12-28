package com.parkwave.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.parkwave.entity.ParkingSlot;
import com.parkwave.service.ParkingSlotService;

@RestController
@RequestMapping("/api/slots")
@CrossOrigin
public class ParkingSlotController {

    @Autowired
    private ParkingSlotService parkingSlotService;

    // Add new parking slot
    @PostMapping
    public ParkingSlot addSlot(@RequestBody ParkingSlot slot) {
        return parkingSlotService.saveSlot(slot);
    }

    // Get all parking slots
    @GetMapping
    public List<ParkingSlot> getAllSlots() {
        return parkingSlotService.getAllSlots();
    }

    // Get slot by ID
    @GetMapping("/{id}")
    public ParkingSlot getSlotById(@PathVariable int id) {
        return parkingSlotService.getSlotById(id);
    }
}
