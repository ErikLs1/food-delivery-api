package com.foodDelivery.api.unitTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.api.controller.impl.ConditionsController;
import com.foodDelivery.api.dto.ConditionsDTO;
import com.foodDelivery.api.model.enums.ConditionType;
import com.foodDelivery.api.service.ConditionsService;
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

@WebMvcTest(ConditionsController.class)
public class ConditionsControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ConditionsService conditionsService;

    @Test
    void testGetAllConditions_Empty() throws Exception {
        when(conditionsService.getAll()).thenReturn(Collections.emptyList());

        mvc.perform(get("/api/conditions"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void testGetAllConditions() throws Exception {
        ConditionsDTO conditionsDTO = new ConditionsDTO(1L, 2L, ConditionType.TEMPERATURE, -10.0, 0.0, null, 0.5, false);
        when(conditionsService.getAll()).thenReturn(List.of(conditionsDTO));

        mvc.perform(get("/api/conditions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].conditionsId").value(1))
                .andExpect(jsonPath("$[0].conditionType").value("TEMPERATURE"));
    }

    @Test
    void testCreateConditions() throws Exception {
        ConditionsDTO requestDTO = new ConditionsDTO(null, 3L, ConditionType.WIND, 10.0, 20.0, null, 0.5, false);
        ConditionsDTO responseDTO = new ConditionsDTO(12L, 3L, ConditionType.WIND, 10.0, 20.0, null, 0.5, false);

        when(conditionsService.create(any())).thenReturn(responseDTO);

        mvc.perform(post("/api/conditions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.conditionsId").value(12L))
                .andExpect(jsonPath("$.vehicleId").value(3L));

    }

    @Test
    void testGetConditionsById() throws Exception {
        ConditionsDTO conditionsDTO = new ConditionsDTO(22L, 2L, ConditionType.WIND, 10.0, 20.0, null, 0.5, false);
        when(conditionsService.getById(22L)).thenReturn(conditionsDTO);

        mvc.perform(get("/api/conditions/22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conditionsId").value(22))
                .andExpect(jsonPath("$.conditionType").value("WIND"));
    }

    @Test
    void testUpdateConditions() throws Exception{
        ConditionsDTO requestDTO = new ConditionsDTO(null, 3L, ConditionType.PHENOMENON, null, null, "Rain", 0.5, false);
        ConditionsDTO responseDTO = new ConditionsDTO(10L, 3L, ConditionType.PHENOMENON, null, null, "Rain", 0.5, false);

        when(conditionsService.update(eq(10L), any())).thenReturn(responseDTO);

        mvc.perform(put("/api/conditions/10")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.conditionsId").value(10))
                .andExpect(jsonPath("$.phenomenon").value("Rain"));
    }


    @Test
    void testDeleteConditions() throws Exception {
        mvc.perform(delete("/api/conditions/13"))
                .andExpect(status().isOk())
                .andExpect(content().string("Conditions with id 13 was deleted!"));
    }
}
