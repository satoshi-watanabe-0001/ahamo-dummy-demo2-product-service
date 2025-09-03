package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.*;
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

@WebMvcTest(SmartphoneOptionsController.class)
class SmartphoneOptionsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SmartphoneProductService smartphoneProductService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getSmartphoneOptions_ReturnsSuccessResponse() throws Exception {
        DataPlanDto dataPlan = DataPlanDto.builder()
                .id("30gb")
                .title("30GB")
                .subtitle("2,970円/月")
                .price("2970")
                .description("データ通信量")
                .build();

        VoiceOptionDto voiceOption = VoiceOptionDto.builder()
                .id("none")
                .title("申し込まない")
                .description("")
                .price("0")
                .build();

        OverseaCallingOptionDto overseaOption = OverseaCallingOptionDto.builder()
                .id("none")
                .title("申し込まない")
                .description("1分/回無料")
                .price("0")
                .build();

        SmartphoneOptionsDto options = SmartphoneOptionsDto.builder()
                .dataPlans(List.of(dataPlan))
                .voiceOptions(List.of(voiceOption))
                .overseaCallingOptions(List.of(overseaOption))
                .build();

        when(smartphoneProductService.getSmartphoneOptions("iphone-15"))
                .thenReturn(options);

        mockMvc.perform(get("/api/smartphone-options/iphone-15"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.dataPlans[0].id").value("30gb"))
                .andExpect(jsonPath("$.data.voiceOptions[0].id").value("none"))
                .andExpect(jsonPath("$.data.overseaCallingOptions[0].id").value("none"));
    }

    @Test
    @WithMockUser
    void getSmartphoneOptions_ServiceException_ReturnsInternalServerError() throws Exception {
        when(smartphoneProductService.getSmartphoneOptions("iphone-15"))
                .thenThrow(new RuntimeException("Database connection failed"));

        mockMvc.perform(get("/api/smartphone-options/iphone-15"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Internal server error"));
    }
}
