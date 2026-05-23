package com.parkwave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.parkwave.entity.Mall;
import com.parkwave.entity.MallParkingSlot;
import com.parkwave.repository.MallRepository;
import com.parkwave.repository.MallParkingSlotRepository;
import com.parkwave.repository.MallBookingRepository;

@Component
public class MallDataInitializer implements CommandLineRunner {

    @Autowired
    private MallRepository mallRepository;

    @Autowired
    private MallParkingSlotRepository mallParkingSlotRepository;

    @Autowired
    private MallBookingRepository mallBookingRepository;

    @Override
    public void run(String... args) throws Exception {
        boolean hasBangalore = mallRepository.findAll().stream()
            .anyMatch(m -> m.getLocation().contains("Bangalore"));
            
        if (mallRepository.count() == 0 || hasBangalore) {
            // Clear old database state to allow clean transition
            mallBookingRepository.deleteAll();
            mallParkingSlotRepository.deleteAll();
            mallRepository.deleteAll();
            initializeMalls();
        }
    }

    private void initializeMalls() {
        // Create 5 popular Hyderabad malls
        Mall[] malls = {
            createMall("Lulu Mall", "Kukatpally, Hyderabad", 500, 3, "Premium shopping destination with 500 parking slots"),
            createMall("Inorbit Mall", "Madhapur, Hyderabad", 300, 2, "Family entertainment center with 300 parking slots"),
            createMall("Sarath City Capital Mall", "Gachibowli, Hyderabad", 400, 3, "One of the largest malls in India with 400 parking slots"),
            createMall("Nexus Forum Mall", "Kukatpally, Hyderabad", 250, 2, "Popular lifestyle mall with 250 parking slots"),
            createMall("GVK One Mall", "Banjara Hills, Hyderabad", 200, 4, "Luxury shopping experience with 200 parking slots")
        };

        for (Mall mall : malls) {
            Mall savedMall = mallRepository.save(mall);
            createParkingSlotsForMall(savedMall);
        }
    }

    private Mall createMall(String name, String location, int totalSlots, int levels, String description) {
        Mall mall = new Mall();
        mall.setName(name);
        mall.setLocation(location);
        mall.setTotalSlots(totalSlots);
        mall.setParkingLevels(levels);
        mall.setDescription(description);
        return mall;
    }

    private void createParkingSlotsForMall(Mall mall) {
        int slotsPerLevel = mall.getTotalSlots() / mall.getParkingLevels();
        
        for (int level = 1; level <= mall.getParkingLevels(); level++) {
            int slotsOnThisLevel = slotsPerLevel;
            if (level == mall.getParkingLevels()) {
                // Last level gets remaining slots
                slotsOnThisLevel = mall.getTotalSlots() - (slotsPerLevel * (level - 1));
            }
            
            createLevelSlots(mall, level, slotsOnThisLevel);
        }
    }

    private void createLevelSlots(Mall mall, int level, int slotCount) {
        char[] zones = {'A', 'B', 'C', 'D'};
        int slotsPerZone = slotCount / zones.length;
        
        for (int zoneIndex = 0; zoneIndex < zones.length; zoneIndex++) {
            char zone = zones[zoneIndex];
            int zoneSlotCount = slotsPerZone;
            
            if (zoneIndex == zones.length - 1) {
                zoneSlotCount = slotCount - (slotsPerZone * zoneIndex);
            }
            
            for (int slotNum = 1; slotNum <= zoneSlotCount; slotNum++) {
                MallParkingSlot slot = new MallParkingSlot();
                slot.setMall(mall);
                slot.setSlotNumber("L" + level + "-" + zone + slotNum);
                slot.setLevel(level);
                slot.setRowPosition(zone);
                slot.setColumnPosition(slotNum);
                slot.setZone(String.valueOf(zone));
                slot.setPriority(determinePriority(level, zone));
                slot.setStatus("AVAILABLE");
                slot.setSlotType("REGULAR");
                
                // Add some handicapped and VIP slots
                if (slotNum % 20 == 0) {
                    slot.setSlotType("HANDICAPPED");
                } else if (slotNum % 25 == 0) {
                    slot.setSlotType("VIP");
                }
                
                mallParkingSlotRepository.save(slot);
            }
        }
    }
    
    private String determinePriority(int level, char zone) {
        if (level == 1) return "NEAR_ENTRANCE";
        if (zone <= 'B') return "MIDDLE";
        return "FAR";
    }
}
