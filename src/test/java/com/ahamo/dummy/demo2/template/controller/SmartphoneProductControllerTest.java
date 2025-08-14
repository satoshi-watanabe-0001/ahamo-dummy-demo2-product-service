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

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SmartphoneProductController.class)
class SmartphoneProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SmartphoneProductService smartphoneProductService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getSmartphones_ReturnsSuccessResponse() throws Exception {
        SmartphoneProductDto smartphone = SmartphoneProductDto.builder()
                .id("1")
                .name("iPhone 16e")
                .brand("Apple")
                .price("43,670円〜")
                .has5G(true)
                .build();

        SmartphoneApiResponse apiResponse = SmartphoneApiResponse.builder()
                .smartphones(List.of(smartphone))
                .totalCount(1)
                .currentPage(0)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(smartphoneProductService.getSmartphones(null, 0, 10))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/smartphones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.smartphones[0].id").value("1"))
                .andExpect(jsonPath("$.data.smartphones[0].name").value("iPhone 16e"))
                .andExpect(jsonPath("$.data.totalCount").value(1));
    }

    @Test
    @WithMockUser
    void getSmartphones_WithBrandFilter_ReturnsFilteredResults() throws Exception {
        SmartphoneProductDto smartphone = SmartphoneProductDto.builder()
                .id("1")
                .name("iPhone 16e")
                .brand("Apple")
                .price("43,670円〜")
                .has5G(true)
                .build();

        SmartphoneApiResponse apiResponse = SmartphoneApiResponse.builder()
                .smartphones(List.of(smartphone))
                .totalCount(1)
                .currentPage(0)
                .totalPages(1)
                .hasNext(false)
                .hasPrevious(false)
                .build();

        when(smartphoneProductService.getSmartphones("Apple", 0, 10))
                .thenReturn(apiResponse);

        mockMvc.perform(get("/api/v1/smartphones")
                        .param("brand", "Apple"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.smartphones[0].brand").value("Apple"));
    }

    @Test
    @WithMockUser
    void getSmartphoneById_ExistingId_ReturnsSmartphone() throws Exception {
        ColorOptionDto colorOption = ColorOptionDto.builder()
                .name("ホワイト")
                .colorCode("#FFFFFF")
                .build();

        SmartphoneProductDto smartphone = SmartphoneProductDto.builder()
                .id("1")
                .name("iPhone 16e")
                .brand("Apple")
                .price("43,670円〜")
                .colorOptions(List.of(colorOption))
                .features(Arrays.asList("いつでもカエドキプログラム適用時", "お客様負担額"))
                .specifications(Arrays.asList("A16 Bionicチップ", "48MPカメラシステム"))
                .has5G(true)
                .build();

        when(smartphoneProductService.getSmartphoneById("1"))
                .thenReturn(smartphone);

        mockMvc.perform(get("/api/v1/smartphones/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value("1"))
                .andExpect(jsonPath("$.data.name").value("iPhone 16e"))
                .andExpect(jsonPath("$.data.colorOptions[0].name").value("ホワイト"));
    }

    @Test
    @WithMockUser
    void getSmartphoneById_NonExistingId_ReturnsNotFound() throws Exception {
        when(smartphoneProductService.getSmartphoneById("999"))
                .thenReturn(null);

        mockMvc.perform(get("/api/v1/smartphones/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getSmartphoneOptions_ReturnsOptions() throws Exception {
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

        when(smartphoneProductService.getSmartphoneOptions("1"))
                .thenReturn(options);

        mockMvc.perform(get("/api/v1/smartphones/1/options"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.dataPlans[0].id").value("30gb"))
                .andExpect(jsonPath("$.data.voiceOptions[0].id").value("none"))
                .andExpect(jsonPath("$.data.overseaCallingOptions[0].id").value("none"));
    }
}
