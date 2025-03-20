package com.foodDelivery.api.unitTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.api.controller.impl.WeatherDataController;
import com.foodDelivery.api.dto.WeatherDataDTO;
import com.foodDelivery.api.service.WeatherDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(WeatherDataController.class)
public class WeatherDataControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private WeatherDataService weatherDataService;

    @Test
    void testGetAllWeatherData_Empty() throws Exception {
        when(weatherDataService.getAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/weather-data"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetAllWeatherData() throws Exception {
        WeatherDataDTO weatherDataDTO = new WeatherDataDTO(
                20L,
                3L,
                5.5,
                3.2,
                "Clear",
                LocalDateTime.of(2025, 10, 13, 7, 35, 0)
        );
        when(weatherDataService.getAll()).thenReturn(List.of(weatherDataDTO));

        mvc.perform(get("/api/weather-data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].weatherDataId").value(20L))
                .andExpect(jsonPath("$[0].cityId").value(3L))
                .andExpect(jsonPath("$[0].airTemperature").value(5.5))
                .andExpect(jsonPath("$[0].windSpeed").value(3.2))
                .andExpect(jsonPath("$[0].weatherPhenomenon").value("Clear"));
    }

    @Test
    void testCreateWeatherData() throws Exception {
        WeatherDataDTO requestDTO = new WeatherDataDTO(
                null,
                2L,
                2.9,
                3.4,
                "Clear",
                LocalDateTime.of(2025, 10, 13, 7, 35, 0)
        );

        WeatherDataDTO responseDTO = new WeatherDataDTO(
                20L,
                2L,
                2.9,
                3.4,
                "Clear",
                LocalDateTime.of(2025, 10, 13, 7, 35, 0)
        );

        when(weatherDataService.create(any())).thenReturn(responseDTO);

        mvc.perform(post("/api/weather-data")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.weatherDataId").value(20L))
                .andExpect(jsonPath("$.cityId").value(2L))
                .andExpect(jsonPath("$.airTemperature").value(2.9))
                .andExpect(jsonPath("$.windSpeed").value(3.4))
                .andExpect(jsonPath("$.weatherPhenomenon").value("Clear"));

    }

    @Test
    void testGetWeatherDataById() throws Exception {
        WeatherDataDTO weatherDataDTO = new WeatherDataDTO(
                20L,
                2L,
                -1.0,
                4.5,
                "Light snow shower",
                LocalDateTime.of(2025, 10, 13, 7, 35, 0)
        );

        when(weatherDataService.getById(20L)).thenReturn(weatherDataDTO);

        mvc.perform(get("/api/weather-data/20"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weatherDataId").value(20L))
                .andExpect(jsonPath("$.weatherPhenomenon").value("Light snow shower"));
    }

    @Test
    void testUpdateWeatherData() throws Exception{
        WeatherDataDTO requestDTO = new WeatherDataDTO(
                null,
                5L,
                0.0,
                10.0,
                "Light shower",
                LocalDateTime.of(2025, 10, 13, 7, 35, 0)
        );
        WeatherDataDTO responseDTO = new WeatherDataDTO(
                2L,
                5L,
                0.0,
                10.0,
                "Light shower",
                LocalDateTime.of(2025, 10, 13, 7, 35, 0)
        );

        when(weatherDataService.update(eq(2L), any())).thenReturn(responseDTO);

        mvc.perform(put("/api/weather-data/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.weatherDataId").value(2L))
                .andExpect(jsonPath("$.weatherPhenomenon").value("Light shower"))
                .andExpect(jsonPath("$.windSpeed").value(10.0));
    }


    @Test
    void testDeleteWeatherData() throws Exception {
        mvc.perform(delete("/api/weather-data/33"))
                .andExpect(status().isOk())
                .andExpect(content().string("WeatherData with id 33 was deleted!"));
    }
}
