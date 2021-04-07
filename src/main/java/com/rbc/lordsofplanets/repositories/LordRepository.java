package com.rbc.lordsofplanets.repositories;

import com.rbc.lordsofplanets.models.Lord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LordRepository extends JpaRepository<Lord, Integer> {

    @Query(
            value = "SELECT * FROM lords u ORDER by u.age asc LIMIT 10",
            nativeQuery = true)
    List<Lord> findTop10Youngest();

    @Query(
            value = "SELECT * FROM lords l LEFT JOIN planets p ON l.id = p.lord_id WHERE p.id IS NULL",
            nativeQuery = true)
    List<Lord> findLordsWithoutPlanets();

}
