package com.rbc.lordsofplanets.controllers;

import com.rbc.lordsofplanets.models.Planet;
import com.rbc.lordsofplanets.repositories.LordRepository;
import com.rbc.lordsofplanets.repositories.PlanetRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/planets")
public class PlanetController {

    private final PlanetRepository planetRepository;

    private final LordRepository lordRepository;

    public PlanetController(PlanetRepository planetRepository, LordRepository lordRepository) {
        this.planetRepository = planetRepository;
        this.lordRepository = lordRepository;
    }

    @GetMapping
    public String showPlanetsList(Model model) {
        Planet planet = new Planet();
        model.addAttribute("planets", planetRepository.findAll());
        model.addAttribute("planet", planet);
        return "planets";
    }

    @PostMapping("/add")
    public String addPlanet(@ModelAttribute(value = "planet") Planet planet) {
        planetRepository.save(planet);
        return "redirect:/planets";
    }

    @GetMapping("/edit/{id}")
    public String showEditPlanet(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("planet", planetRepository.findById(id));
        model.addAttribute("lords", lordRepository.findAll());
        return "planet-edit";
    }

    @PostMapping("/edit")
    public String editPlanet(@ModelAttribute(value = "planet") Planet planet) {
        planetRepository.save(planet);
        return "redirect:/planets";
    }

    @GetMapping("/delete/{id}")
    public String deletePlanet(@PathVariable int id) {
        Optional<Planet> planet = planetRepository.findById(id);
        planet.ifPresent(planetRepository::delete);
        return "redirect:/planets";
    }

}
