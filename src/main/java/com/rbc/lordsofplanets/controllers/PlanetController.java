package com.rbc.lordsofplanets.controllers;

import com.rbc.lordsofplanets.models.Lord;
import com.rbc.lordsofplanets.models.Planet;
import com.rbc.lordsofplanets.repositories.LordRepository;
import com.rbc.lordsofplanets.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/planets")
public class PlanetController {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private LordRepository lordRepository;

    @GetMapping
    public List<Planet> getPlanets() {
        return planetRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Planet addLord(@RequestBody Planet planet) {
        return planetRepository.save(planet);
    }

    @GetMapping(path = "{id}")
    public Planet getLord(@PathVariable int id) {
        Optional<Planet> planets = planetRepository.findById(id);
        if (planets.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return planets.get();
    }

    @PutMapping(path = "{id}")
    public Planet updateLordOfPlanet(@PathVariable int id, @RequestParam(name = "lord_id") int lord_id) {
        Optional<Lord> lords = lordRepository.findById(lord_id);
        if (lords.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Optional<Planet> planets = planetRepository.findById(id);
        if (planets.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        Planet planet = planets.get();
        planet.setLord(lords.get());

        planetRepository.save(planet);

        return planet;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable int id) {
        Optional<Planet> planet = planetRepository.findById(id);
        planet.ifPresent(value -> planetRepository.delete(value));
    }

}
