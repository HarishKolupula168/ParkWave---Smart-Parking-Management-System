package com.parkwave.service;

import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parkwave.entity.Mall;
import com.parkwave.repository.MallRepository;

@Service
public class MallService {

    @Autowired
    private MallRepository mallRepository;

    public List<Mall> getAllMalls() {
        return mallRepository.findAll();
    }

    public List<Mall> getRandomMalls(int count) {
        List<Mall> allMalls = mallRepository.findAll();
        Random random = new Random();
        
        if (allMalls.size() <= count) {
            return allMalls;
        }
        
        // Get random malls
        return allMalls.stream()
            .skip(random.nextInt(allMalls.size() - count + 1))
            .limit(count)
            .toList();
    }

    public Mall getMallById(int id) {
        return mallRepository.findById(id).orElse(null);
    }

    public Mall saveMall(Mall mall) {
        return mallRepository.save(mall);
    }
}
