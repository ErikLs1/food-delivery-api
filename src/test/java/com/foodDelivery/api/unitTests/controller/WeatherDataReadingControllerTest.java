package com.foodDelivery.api.unitTests.controller;

import com.foodDelivery.api.controller.impl.WeatherReadingController;
import com.foodDelivery.api.service.WeatherReadingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WeatherReadingController.class)
public class WeatherDataReadingControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private WeatherReadingService weatherReadingService;

    /**
     * Tests that a GET request /api/read-weather returns HTTP 200 status code
     * and that the readWeatherData method called by the controller is called.
     *
     * @throws Exception if GET request fails
     */
    @Test
    void testReadWeatherData() throws Exception {
        mvc.perform(get("/api/read-weather"))
                .andExpect(status().isOk());
        verify(weatherReadingService, times(1)).readWeatherData();
    }
}
