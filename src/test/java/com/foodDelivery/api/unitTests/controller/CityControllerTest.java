package com.foodDelivery.api.unitTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.api.controller.impl.CityController;
import com.foodDelivery.api.dto.CityDTO;
import com.foodDelivery.api.service.CityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CityService cityService;

    @Test
    void testGetAllCities_Empty() throws Exception {
        when(cityService.getAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/city"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetAllCities() throws Exception {
        CityDTO cityDTO = new CityDTO(1L, "Tallinn", "Tallinn-Harku", "26038");
        when(cityService.getAll()).thenReturn(List.of(cityDTO));

        mvc.perform(get("/api/city"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].cityId").value(1))
                .andExpect(jsonPath("$[0].cityName").value("Tallinn"));
    }

    @Test
    void testCreateCity() throws Exception {
        CityDTO requestDTO = new CityDTO(null, "Pärnu", "Parnu", "41803");
        CityDTO responseDTO = new CityDTO(10L, "Pärnu", "Parnu", "41803");

        when(cityService.create(any())).thenReturn(responseDTO);

        mvc.perform(post("/api/city")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cityId").value(10L))
                .andExpect(jsonPath("$.cityName").value("Pärnu"));

    }

    @Test
    void testGetCityById() throws Exception {
        CityDTO cityDTO = new CityDTO(3L, "Tartu", "Tartu-Tõravere", "26242");
        when(cityService.getById(3L)).thenReturn(cityDTO);

        mvc.perform(get("/api/city/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityId").value(3))
                .andExpect(jsonPath("$.wmoCode").value("26242"));
    }

    @Test
    void testUpdateCity() throws Exception{
        CityDTO requestDTO = new CityDTO(null, "Tartu Something", "Tartu-Tõravere", "26242");
        CityDTO responseDTO = new CityDTO(30L, "Tartu Something", "Tartu-Tõravere", "26242");

        when(cityService.update(eq(30L), any())).thenReturn(responseDTO);

        mvc.perform(put("/api/city/30")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cityId").value(30))
                .andExpect(jsonPath("$.cityName").value("Tartu Something"));
    }


    @Test
    void testDeleteCity() throws Exception {
        mvc.perform(delete("/api/city/77"))
                .andExpect(status().isOk())
                .andExpect(content().string("City with id 77 was deleted!"));
    }
}
