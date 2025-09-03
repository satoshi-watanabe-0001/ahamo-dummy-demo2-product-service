package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.DataPlanDto;
import com.ahamo.dummy.demo2.template.controller.dto.DataPlanListResponse;
import com.ahamo.dummy.demo2.template.service.SmartphoneProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DataPlanController.class)
class DataPlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SmartphoneProductService smartphoneProductService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getAllDataPlans_ReturnsSuccessResponse() throws Exception {
        DataPlanDto dataPlan = DataPlanDto.builder()
                .id("30gb")
                .title("30GB")
                .subtitle("2,970円/月")
                .price("2970")
                .description("データ通信量")
                .build();

        DataPlanListResponse response = DataPlanListResponse.builder()
                .dataPlans(List.of(dataPlan))
                .build();

        when(smartphoneProductService.getAllDataPlans())
                .thenReturn(response);

        mockMvc.perform(get("/api/data-plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.dataPlans[0].id").value("30gb"))
                .andExpect(jsonPath("$.data.dataPlans[0].title").value("30GB"));
    }

    @Test
    @WithMockUser
    void getAllDataPlans_ServiceException_ReturnsInternalServerError() throws Exception {
        when(smartphoneProductService.getAllDataPlans())
                .thenThrow(new RuntimeException("Database connection failed"));

        mockMvc.perform(get("/api/data-plans"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Internal server error"));
    }
}
