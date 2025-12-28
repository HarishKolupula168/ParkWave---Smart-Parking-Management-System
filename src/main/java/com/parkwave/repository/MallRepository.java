package com.parkwave.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parkwave.entity.Mall;

public interface MallRepository extends JpaRepository<Mall, Integer> {
}
