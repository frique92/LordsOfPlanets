package com.rbc.lordsofplanets.repositories;

import com.rbc.lordsofplanets.models.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Integer> {
}
