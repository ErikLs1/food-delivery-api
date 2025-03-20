package com.foodDelivery.api.unitTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.api.controller.impl.VehicleController;
import com.foodDelivery.api.dto.VehicleDTO;
import com.foodDelivery.api.model.enums.VehicleType;
import com.foodDelivery.api.service.VehicleService;
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

@WebMvcTest(VehicleController.class)
public class VehicleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private VehicleService vehicleService;

    @Test
    void testGetAllVehicles_Empty() throws Exception {
        when(vehicleService.getAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/vehicle"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetAllVehicles() throws Exception {
        VehicleDTO vehicleDTO = new VehicleDTO(1L, VehicleType.CAR);
        when(vehicleService.getAll()).thenReturn(List.of(vehicleDTO));

        mvc.perform(get("/api/vehicle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].vehicleId").value(1L))
                .andExpect(jsonPath("$[0].vehicleType").value("CAR"));
    }

    @Test
    void testCreateVehicles() throws Exception {
        VehicleDTO requestDTO = new VehicleDTO(null, VehicleType.SCOOTER);
        VehicleDTO responseDTO = new VehicleDTO(8L, VehicleType.SCOOTER);

        when(vehicleService.create(any())).thenReturn(responseDTO);

        mvc.perform(post("/api/vehicle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.vehicleId").value(8L))
                .andExpect(jsonPath("$.vehicleType").value("SCOOTER"));

    }

    @Test
    void testGetVehicleById() throws Exception {
        VehicleDTO vehicleDTO = new VehicleDTO(22L, VehicleType.BIKE);
        when(vehicleService.getById(22L)).thenReturn(vehicleDTO);

        mvc.perform(get("/api/vehicle/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(22L))
                .andExpect(jsonPath("$.vehicleType").value("BIKE"));
    }

    @Test
    void testUpdateVehicle() throws Exception{
        VehicleDTO requestDTO = new VehicleDTO(null, VehicleType.CAR);
        VehicleDTO responseDTO = new VehicleDTO(10L, VehicleType.CAR);

        when(vehicleService.update(eq(10L), any())).thenReturn(responseDTO);

        mvc.perform(put("/api/vehicle/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicleId").value(10L))
                .andExpect(jsonPath("$.vehicleType").value("CAR"));
    }


    @Test
    void testDeleteVehicle() throws Exception {
        mvc.perform(delete("/api/vehicle/13"))
                .andExpect(status().isOk())
                .andExpect(content().string("Vehicle with id 13 was deleted!"));
    }
}
