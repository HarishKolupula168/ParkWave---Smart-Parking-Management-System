package com.parkwave.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.parkwave.entity.MallBooking;

public interface MallBookingRepository extends JpaRepository<MallBooking, Integer> {
    List<MallBooking> findByUserId(int userId);
    List<MallBooking> findByMallId(int mallId);
    List<MallBooking> findByUserIdAndStatus(int userId, String status);
    
    long countByMallId(int mallId);
    
    @Query("SELECT COUNT(b) FROM MallBooking b WHERE b.mall.id = :mallId AND b.status = 'ACTIVE'")
    long countActiveBookingsByMall(@Param("mallId") int mallId);
}
