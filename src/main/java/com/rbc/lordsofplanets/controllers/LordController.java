package com.rbc.lordsofplanets.controllers;

import com.rbc.lordsofplanets.models.Lord;
import com.rbc.lordsofplanets.repositories.LordRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/lords")
public class LordController {

    private final LordRepository lordRepository;

    public LordController(LordRepository lordRepository) {
        this.lordRepository = lordRepository;
    }

    @GetMapping
    public String showLordList(Model model) {
        Lord lord = new Lord();
        model.addAttribute("lord", lord);
        model.addAttribute("lords", lordRepository.findAll());
        return "lords";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute(value = "planet") Lord lord) {
        lordRepository.save(lord);
        return "redirect:/lords";
    }

    @GetMapping("/edit/{id}")
    public String showEditLord(Model model, @PathVariable(value = "id") int id) {
        model.addAttribute("lord", lordRepository.findById(id));
        return "lord-edit";
    }

    @PostMapping("/edit")
    public String editLord(@ModelAttribute(value = "lord") Lord lord) {
        lordRepository.save(lord);
        return "redirect:/lords";
    }

    @GetMapping("/delete/{id}")
    public String deleteLord(@PathVariable int id) {
        Optional<Lord> lord = lordRepository.findById(id);
        lord.ifPresent(lordRepository::delete);
        return "redirect:/lords";
    }

    @GetMapping("/lazy")
    public String showLordsWithoutPlanets(Model model) {
        model.addAttribute("lords", lordRepository.findLordsWithoutPlanets());
        model.addAttribute("pageTitle", "Lazy lords");
        return "filter-lords";
    }

    @GetMapping("/top-youngest")
    public String showTopYoungestLords(Model model) {
        model.addAttribute("lords", lordRepository.findTop10Youngest());
        model.addAttribute("pageTitle", "Top 10 youngest lords");
        return "filter-lords";
    }

}
