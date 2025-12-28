package com.parkwave.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.parkwave.entity.MallParkingSlot;
import com.parkwave.service.MallParkingSlotService;

@RestController
@RequestMapping("/api/mall-parking")
@CrossOrigin
public class MallParkingController {

    @Autowired
    private MallParkingSlotService mallParkingSlotService;

    @GetMapping("/slots/{mallId}")
    public List<MallParkingSlot> getSlotsByMall(@PathVariable int mallId) {
        return mallParkingSlotService.getSlotsByMall(mallId);
    }

    @GetMapping("/available/{mallId}")
    public List<MallParkingSlot> getAvailableSlotsByMall(@PathVariable int mallId) {
        return mallParkingSlotService.getAvailableSlotsByMall(mallId);
    }

    @GetMapping("/slots/{mallId}/level/{level}")
    public List<MallParkingSlot> getSlotsByMallAndLevel(@PathVariable int mallId, @PathVariable int level) {
        return mallParkingSlotService.getSlotsByMallAndLevel(mallId, level);
    }

    @PostMapping("/book/{slotId}")
    public MallParkingSlot bookSlot(@PathVariable int slotId) {
        MallParkingSlot result = mallParkingSlotService.bookSlot(slotId);
        if (result == null) {
            throw new RuntimeException("Slot is not available for booking");
        }
        return result;
    }

    @PostMapping("/release/{slotId}")
    public String releaseSlot(@PathVariable int slotId) {
        mallParkingSlotService.releaseSlot(slotId);
        return "Slot released successfully";
    }

    @GetMapping("/slot/{slotId}")
    public MallParkingSlot getSlotById(@PathVariable int slotId) {
        return mallParkingSlotService.getSlotById(slotId);
    }
}
