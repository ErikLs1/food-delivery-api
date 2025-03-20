package com.foodDelivery.api.unitTests.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foodDelivery.api.controller.impl.DeliveryFeeController;
import com.foodDelivery.api.dto.DeliveryFeeRequestDTO;
import com.foodDelivery.api.service.DeliveryFeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeliveryFeeController.class)
public class DeliveryFeeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DeliveryFeeService deliveryFeeService;

    /**
     * Test that a GET request /api/delivery-fee with a valid JSON payload returns
     * HTTP 200 status code and that response contains expected delivery fee.
     *
     * @throws Exception if the GET request fails.
     */
    @Test
    void testGetDeliveryFeeController() throws Exception {
        DeliveryFeeRequestDTO request = new DeliveryFeeRequestDTO();
        request.setCityName("Tallinn");
        request.setVehicleType("BIKE");
        LocalDateTime observationTime = LocalDateTime.of(2025, 3, 20, 10, 0, 0);
        request.setObservationTime(observationTime);

        when(deliveryFeeService.calculateDeliveryFee("Tallinn", "BIKE", observationTime))
                .thenReturn(3.0);

        mvc.perform(get("/api/delivery-fee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalFee").value(3.0));
    }
}
