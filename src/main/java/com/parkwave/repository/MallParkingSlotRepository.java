package com.parkwave.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.parkwave.entity.MallParkingSlot;

public interface MallParkingSlotRepository extends JpaRepository<MallParkingSlot, Integer> {
    
    List<MallParkingSlot> findByMallId(int mallId);
    
    List<MallParkingSlot> findByMallIdAndLevel(int mallId, int level);
    
    @Query("SELECT s FROM MallParkingSlot s WHERE s.mall.id = :mallId AND s.status = 'AVAILABLE'")
    List<MallParkingSlot> findAvailableSlotsByMall(@Param("mallId") int mallId);
    
    long countByMallId(int mallId);
    
    @Query("SELECT COUNT(s) FROM MallParkingSlot s WHERE s.mall.id = :mallId AND s.status = 'AVAILABLE'")
    long countAvailableSlotsByMall(@Param("mallId") int mallId);
    
    boolean existsBySlotNumberAndMallId(String slotNumber, int mallId);
}
