package com.parkwave.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.parkwave.entity.Mall;
import com.parkwave.entity.MallParkingSlot;
import com.parkwave.repository.MallRepository;
import com.parkwave.repository.MallParkingSlotRepository;

@Component
public class MallDataInitializer implements CommandLineRunner {

    @Autowired
    private MallRepository mallRepository;

    @Autowired
    private MallParkingSlotRepository mallParkingSlotRepository;

    @Override
    public void run(String... args) throws Exception {
        if (mallRepository.count() == 0) {
            initializeMalls();
        }
    }

    private void initializeMalls() {
        // Create 5 major malls
        Mall[] malls = {
            createMall("Phoenix Marketcity", "Whitefield, Bangalore", 500, 3, "Premium shopping destination with 500 parking slots"),
            createMall("Orion Mall", "Rajajinagar, Bangalore", 300, 2, "Family entertainment center with 300 parking slots"),
            createMall("Mantri Square", "Malleshwaram, Bangalore", 400, 3, "One of the largest malls with 400 parking slots"),
            createMall("Forum Mall", "Koramangala, Bangalore", 250, 2, "Popular lifestyle mall with 250 parking slots"),
            createMall("UB City", "Vittal Mallya Road, Bangalore", 200, 4, "Luxury mall with 200 parking slots")
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
