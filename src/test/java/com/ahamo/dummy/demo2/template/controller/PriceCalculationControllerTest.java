package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.PriceCalculationRequest;
import com.ahamo.dummy.demo2.template.controller.dto.PriceCalculationResponse;
import com.ahamo.dummy.demo2.template.service.SmartphoneProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PriceCalculationController.class)
class PriceCalculationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SmartphoneProductService smartphoneProductService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void calculatePrice_ReturnsSuccessResponse() throws Exception {
        PriceCalculationRequest request = new PriceCalculationRequest();
        request.setDeviceId("iphone-15");
        request.setDataPlanId("plan-20gb");
        request.setVoiceOptionId("voice-5min");
        request.setOverseaOptionId("oversea-unlimited");

        PriceCalculationResponse.PriceBreakdownItem item = PriceCalculationResponse.PriceBreakdownItem.builder()
                .name("20GBプラン")
                .price(2970)
                .build();

        PriceCalculationResponse response = PriceCalculationResponse.builder()
                .totalPrice(2970)
                .monthlyPrice(2970)
                .breakdown(List.of(item))
                .build();

        when(smartphoneProductService.calculatePrice(any(PriceCalculationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.totalPrice").value(2970))
                .andExpect(jsonPath("$.data.monthlyPrice").value(2970))
                .andExpect(jsonPath("$.data.breakdown[0].name").value("20GBプラン"));
    }

    @Test
    @WithMockUser
    void calculatePrice_ServiceException_ReturnsInternalServerError() throws Exception {
        PriceCalculationRequest request = new PriceCalculationRequest();
        request.setDeviceId("iphone-15");

        when(smartphoneProductService.calculatePrice(any(PriceCalculationRequest.class)))
                .thenThrow(new RuntimeException("Database connection failed"));

        mockMvc.perform(post("/api/calculate-price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Internal server error"));
    }
}
