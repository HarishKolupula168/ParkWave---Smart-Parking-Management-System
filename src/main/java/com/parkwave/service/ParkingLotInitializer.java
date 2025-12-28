package com.parkwave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.parkwave.entity.ParkingSlot;
import com.parkwave.repository.ParkingSlotRepository;

@Component
public class ParkingLotInitializer implements CommandLineRunner {

    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    @Override
    public void run(String... args) throws Exception {
        if (parkingSlotRepository.count() == 0) {
            initializeParkingLot();
        }
    }

    private void initializeParkingLot() {
        // Create a 10x10 parking lot (100 slots)
        // Rows A-J, Columns 1-10
        
        char[] rows = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J'};
        
        for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
            char row = rows[rowIndex];
            String priority = determinePriority(rowIndex);
            
            for (int col = 1; col <= 10; col++) {
                ParkingSlot slot = new ParkingSlot();
                slot.setSlotNumber(row + "" + col);
                slot.setRowPosition(row);
                slot.setColumnPosition(col);
                slot.setPriority(priority);
                slot.setStatus("AVAILABLE");
                parkingSlotRepository.save(slot);
            }
        }
        
        // Add additional premium slots (20 more) - 120 total
        for (int i = 1; i <= 20; i++) {
            ParkingSlot premiumSlot = new ParkingSlot();
            premiumSlot.setSlotNumber("P" + i);
            premiumSlot.setRowPosition('P');
            premiumSlot.setColumnPosition(i);
            premiumSlot.setPriority("PREMIUM");
            premiumSlot.setStatus("AVAILABLE");
            parkingSlotRepository.save(premiumSlot);
        }
    }
    
    private String determinePriority(int rowIndex) {
        if (rowIndex <= 2) return "FRONT";  // Rows A, B, C - Front priority
        if (rowIndex <= 6) return "MIDDLE"; // Rows D, E, F, G - Middle priority  
        return "BACK";                      // Rows H, I, J - Back priority
    }
}
