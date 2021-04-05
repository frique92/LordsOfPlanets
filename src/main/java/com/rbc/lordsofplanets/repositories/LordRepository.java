package com.rbc.lordsofplanets.repositories;

import com.rbc.lordsofplanets.models.Lord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LordRepository extends JpaRepository<Lord, Integer> {
}
