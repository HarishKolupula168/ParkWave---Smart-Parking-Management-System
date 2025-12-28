package com.parkwave.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.parkwave.entity.Mall;
import com.parkwave.service.MallService;

@RestController
@RequestMapping("/api/malls")
@CrossOrigin
public class MallController {

    @Autowired
    private MallService mallService;

    @GetMapping
    public List<Mall> getAllMalls() {
        return mallService.getAllMalls();
    }

    @GetMapping("/random")
    public List<Mall> getRandomMalls(@RequestParam(defaultValue = "5") int count) {
        return mallService.getRandomMalls(count);
    }

    @GetMapping("/{id}")
    public Mall getMallById(@PathVariable int id) {
        return mallService.getMallById(id);
    }

    @PostMapping
    public Mall createMall(@RequestBody Mall mall) {
        return mallService.saveMall(mall);
    }
}
