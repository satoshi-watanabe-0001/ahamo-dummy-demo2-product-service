package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.VoiceOptionDto;
import com.ahamo.dummy.demo2.template.controller.dto.VoiceOptionListResponse;
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

@WebMvcTest(VoiceOptionController.class)
class VoiceOptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SmartphoneProductService smartphoneProductService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getAllVoiceOptions_ReturnsSuccessResponse() throws Exception {
        VoiceOptionDto voiceOption = VoiceOptionDto.builder()
                .id("voice-5min")
                .title("5分通話無料オプション")
                .description("5分以内の国内通話が無料")
                .price("770")
                .build();

        VoiceOptionListResponse response = VoiceOptionListResponse.builder()
                .voiceOptions(List.of(voiceOption))
                .build();

        when(smartphoneProductService.getAllVoiceOptions())
                .thenReturn(response);

        mockMvc.perform(get("/api/voice-options"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.voiceOptions[0].id").value("voice-5min"))
                .andExpect(jsonPath("$.data.voiceOptions[0].title").value("5分通話無料オプション"));
    }

    @Test
    @WithMockUser
    void getAllVoiceOptions_ServiceException_ReturnsInternalServerError() throws Exception {
        when(smartphoneProductService.getAllVoiceOptions())
                .thenThrow(new RuntimeException("Database connection failed"));

        mockMvc.perform(get("/api/voice-options"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").value("Internal server error"));
    }
}
