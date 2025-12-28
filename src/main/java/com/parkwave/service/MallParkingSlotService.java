package com.parkwave.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parkwave.entity.MallParkingSlot;
import com.parkwave.repository.MallParkingSlotRepository;

@Service
public class MallParkingSlotService {

    @Autowired
    private MallParkingSlotRepository mallParkingSlotRepository;

    public List<MallParkingSlot> getSlotsByMall(int mallId) {
        return mallParkingSlotRepository.findByMallId(mallId);
    }

    public List<MallParkingSlot> getAvailableSlotsByMall(int mallId) {
        return mallParkingSlotRepository.findAvailableSlotsByMall(mallId);
    }

    public List<MallParkingSlot> getSlotsByMallAndLevel(int mallId, int level) {
        return mallParkingSlotRepository.findByMallIdAndLevel(mallId, level);
    }

    public MallParkingSlot bookSlot(int slotId) {
        MallParkingSlot slot = mallParkingSlotRepository.findById(slotId).orElse(null);
        if (slot != null && "AVAILABLE".equals(slot.getStatus())) {
            slot.setStatus("BOOKED");
            return mallParkingSlotRepository.save(slot);
        }
        return null;
    }

    public void releaseSlot(int slotId) {
        MallParkingSlot slot = mallParkingSlotRepository.findById(slotId).orElse(null);
        if (slot != null && "BOOKED".equals(slot.getStatus())) {
            slot.setStatus("AVAILABLE");
            mallParkingSlotRepository.save(slot);
        }
    }

    public MallParkingSlot getSlotById(int id) {
        return mallParkingSlotRepository.findById(id).orElse(null);
    }

    // Admin methods
    public MallParkingSlot createSlot(MallParkingSlot slot) {
        // Check if slot number already exists for this mall
        if (mallParkingSlotRepository.existsBySlotNumberAndMallId(slot.getSlotNumber(), slot.getMall().getId())) {
            throw new RuntimeException("Slot number already exists for this mall");
        }
        return mallParkingSlotRepository.save(slot);
    }

    public MallParkingSlot updateSlot(int slotId, MallParkingSlot updatedSlot) {
        MallParkingSlot existingSlot = mallParkingSlotRepository.findById(slotId).orElse(null);
        if (existingSlot == null) {
            return null;
        }
        
        // Update fields
        existingSlot.setSlotNumber(updatedSlot.getSlotNumber());
        existingSlot.setLevel(updatedSlot.getLevel());
        existingSlot.setRowPosition(updatedSlot.getRowPosition());
        existingSlot.setColumnPosition(updatedSlot.getColumnPosition());
        existingSlot.setZone(updatedSlot.getZone());
        existingSlot.setPriority(updatedSlot.getPriority());
        existingSlot.setSlotType(updatedSlot.getSlotType());
        
        return mallParkingSlotRepository.save(existingSlot);
    }

    public boolean deleteSlot(int slotId) {
        MallParkingSlot slot = mallParkingSlotRepository.findById(slotId).orElse(null);
        if (slot != null && "AVAILABLE".equals(slot.getStatus())) {
            mallParkingSlotRepository.delete(slot);
            return true;
        }
        return false;
    }
}
