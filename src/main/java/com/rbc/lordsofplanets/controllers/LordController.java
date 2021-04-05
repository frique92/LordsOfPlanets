package com.rbc.lordsofplanets.controllers;

import com.rbc.lordsofplanets.models.Lord;
import com.rbc.lordsofplanets.models.Planet;
import com.rbc.lordsofplanets.repositories.LordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/lords")
public class LordController {

    @Autowired
    private LordRepository lordRepository;

    @GetMapping
    public List<Lord> getLords() {
        return lordRepository.findAll();
    }

    @PostMapping(consumes = "application/json")
    public Lord addLord(@RequestBody Lord lord) {
        return lordRepository.save(lord);
    }

    @GetMapping(path = "{id}")
    public Lord getLord(@PathVariable int id) {
        Optional<Lord> lords = lordRepository.findById(id);
        if (lords.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return lords.get();
    }

    @GetMapping("/topyoungest")
    public List<Lord> getTopYoungestLords() {
        return lordRepository.findTop10Youngest();
    }

    @GetMapping("/lazy")
    public List<Lord> getLordsWithoutPlanets() {
        return lordRepository.findLordsWithoutPlanets();
    }

}