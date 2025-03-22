package com.foodDelivery.api.unitTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.api.controller.impl.BaseFeeController;
import com.foodDelivery.api.dto.BaseFeeDTO;
import com.foodDelivery.api.model.BaseFee;
import com.foodDelivery.api.service.BaseFeeService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for BaseFeeController that test REST endpoints.
 */
@WebMvcTest(BaseFeeController.class)
public class BaseFeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private BaseFeeService baseFeeService;

    /**
     * Tests that when there is no base fee, controller returns empty JSON.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    void testGetAllBaseFees_Empty() throws Exception {
        when(baseFeeService.getAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/base-fee"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    /**
     * Tests that retrieving all base fees returns the expected list.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    void testGetAllBaseFees() throws Exception {
        BaseFeeDTO baseFeeDTO = new BaseFeeDTO(12L, 3L, 4L, 4.0);
        when(baseFeeService.getAll()).thenReturn(List.of(baseFeeDTO));

        mvc.perform(get("/api/base-fee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].baseFeeId").value(12L))
                .andExpect(jsonPath("$[0].cityId").value(3L))
                .andExpect(jsonPath("$[0].vehicleId").value(4L))
                .andExpect(jsonPath("$[0].vehicleFee").value(4.0));
    }

    /**
     * Tests that a new base fee is created.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    void testCreateBaseFee() throws Exception {
        BaseFeeDTO request = new BaseFeeDTO(null, 2L, 3L, 3.0);
        BaseFeeDTO response = new BaseFeeDTO(6L, 2L, 3L, 3.0);

        when(baseFeeService.create(any())).thenReturn(response);

        mvc.perform(post("/api/base-fee")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.baseFeeId").value(6L))
                .andExpect(jsonPath("$.cityId").value(2L))
                .andExpect(jsonPath("$.vehicleId").value(3L))
                .andExpect(jsonPath("$.vehicleFee").value(3.0));

    }

    /**
     * Tests retrieving a base fee by id.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    void testGetBaseFeeById() throws Exception {
        BaseFeeDTO baseFeeDTO = new BaseFeeDTO(10L, 2L, 3L, 3.0);
        when(baseFeeService.getById(10L)).thenReturn(baseFeeDTO);

        mvc.perform(get("/api/base-fee/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseFeeId").value(10L))
                .andExpect(jsonPath("$.vehicleFee").value(3.0));
    }

    /**
     * Tests updating an existing base fee.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    void testUpdateBaseFee() throws Exception{
        BaseFeeDTO requestDTO = new BaseFeeDTO(null, 2L, 3L, 4.0);
        BaseFeeDTO responseDTO = new BaseFeeDTO(20L, 2L, 3L, 4.0);

        when(baseFeeService.update(eq(20L), any())).thenReturn(responseDTO);

        mvc.perform(put("/api/base-fee/20")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.baseFeeId").value(20L))
                .andExpect(jsonPath("$.vehicleFee").value(4.0));

    }

    /**
     * Tests that deleting a base fee returns a success message.
     *
     * @throws Exception if an error occurs during the request.
     */
    @Test
    void testDeleteBaseFee() throws Exception {
        mvc.perform(delete("/api/base-fee/2"))
                .andExpect(status().isOk())
                .andExpect(content().string("Base fee with id 2 was deleted!"));
    }
}
