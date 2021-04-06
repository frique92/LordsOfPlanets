package com.rbc.lordsofplanets.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rbc.lordsofplanets.models.Lord;
import com.rbc.lordsofplanets.models.Planet;
import com.rbc.lordsofplanets.repositories.LordRepository;
import com.rbc.lordsofplanets.repositories.PlanetRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PlanetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LordRepository lordRepository;

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    void getPlanets() throws Exception{

        MvcResult mvcResult = this.mockMvc.perform(get("/api/planets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();

        assertNotNull(contentAsString);

        Planet planet = new Planet();
        planet.setName("Earth");

        planetRepository.save(planet);

        mvcResult = this.mockMvc.perform(get("/api/planets"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        contentAsString = mvcResult.getResponse().getContentAsString();

        assertEquals(contentAsString, "[{\"id\":1,\"name\":\"Earth\",\"lord\":null}]");

    }

    @Test
    void addPlanet() {
    }

    @Test
    void getPlanet() {
    }

    @Test
    void updateLordOfPlanet() {
    }

    @Test
    void deletePlanet() {
    }
}