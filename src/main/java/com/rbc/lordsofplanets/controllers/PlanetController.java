package com.rbc.lordsofplanets.controllers;

import com.rbc.lordsofplanets.models.Lord;
import com.rbc.lordsofplanets.models.Planet;
import com.rbc.lordsofplanets.repositories.LordRepository;
import com.rbc.lordsofplanets.repositories.PlanetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/planets")
public class PlanetController {

    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private LordRepository lordRepository;

//    @GetMapping
//    public List<Planet> getPlanets() {
//        return planetRepository.findAll();
//    }

    @PostMapping(consumes = "application/json")
    public Planet addPlanet(@RequestBody Planet planet) {
        return planetRepository.save(planet);
    }

    @GetMapping(path = "{id}")
    public Planet getPlanet(@PathVariable int id) {
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

//    @DeleteMapping("/delete/{id}")
//    public void deletePlanet(@PathVariable int id) {
//        Optional<Planet> planet = planetRepository.findById(id);
//        planet.ifPresent(value -> planetRepository.delete(value));
//    }

    @GetMapping
    public String showPlanetsList(Model model) {
        Planet planet = new Planet();
        model.addAttribute("planets", planetRepository.findAll());
        model.addAttribute("planet", planet);
//        model.addAttribute("lords", lordRepository.findAll());
        return "planets";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "planet") Planet planet) {
        planetRepository.save(planet);
        return "redirect:/planets";
    }

    @GetMapping("/delete/{id}")
    public String deletePlanet(@PathVariable int id) {
        Optional<Planet> planet = planetRepository.findById(id);
        planet.ifPresent(value -> planetRepository.delete(value));
        return "redirect:/planets";
    }

}
