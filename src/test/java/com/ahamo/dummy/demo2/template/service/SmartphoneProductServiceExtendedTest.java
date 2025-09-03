package com.ahamo.dummy.demo2.template.service;

import com.ahamo.dummy.demo2.template.controller.dto.*;
import com.ahamo.dummy.demo2.template.domain.entity.*;
import com.ahamo.dummy.demo2.template.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmartphoneProductServiceExtendedTest {

    @Mock
    private SmartphoneProductRepository smartphoneProductRepository;

    @Mock
    private DataPlanRepository dataPlanRepository;

    @Mock
    private VoiceOptionRepository voiceOptionRepository;

    @Mock
    private OverseaCallingOptionRepository overseaCallingOptionRepository;

    @Mock
    private SmartphoneStockRepository smartphoneStockRepository;

    @Mock
    private PriceCalculationRepository priceCalculationRepository;

    @InjectMocks
    private SmartphoneProductService smartphoneProductService;

    private SmartphoneProduct testSmartphone;
    private DataPlan testDataPlan;
    private VoiceOption testVoiceOption;

    @BeforeEach
    void setUp() {
        testSmartphone = SmartphoneProduct.builder()
                .id("1")
                .name("iPhone 16e")
                .brand("Apple")
                .price("43,670円〜")
                .has5G(true)
                .build();

        testDataPlan = DataPlan.builder()
                .id("plan-20gb")
                .title("20GBプラン")
                .price("2970")
                .description("20GB データプラン")
                .build();

        testVoiceOption = VoiceOption.builder()
                .id("voice-5min")
                .title("5分通話無料")
                .price("770")
                .description("5分以内の国内通話が無料")
                .build();
    }

    @Test
    void searchSmartphones_ReturnsFilteredResults() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<SmartphoneProduct> page = new PageImpl<>(List.of(testSmartphone));

        when(smartphoneProductRepository.findByNameContainingIgnoreCase(eq("iPhone"), any(Pageable.class)))
                .thenReturn(page);

        SmartphoneApiResponse result = smartphoneProductService.searchSmartphones("iPhone", 0, 10);

        assertThat(result.getSmartphones()).hasSize(1);
        assertThat(result.getSmartphones().get(0).getName()).isEqualTo("iPhone 16e");
        assertThat(result.getTotalCount()).isEqualTo(1);
    }

    @Test
    void getAllDataPlans_ReturnsAllPlans() {
        when(dataPlanRepository.findAll()).thenReturn(List.of(testDataPlan));

        DataPlanListResponse result = smartphoneProductService.getAllDataPlans();

        assertThat(result.getDataPlans()).hasSize(1);
        assertThat(result.getDataPlans().get(0).getId()).isEqualTo("plan-20gb");
        assertThat(result.getDataPlans().get(0).getTitle()).isEqualTo("20GBプラン");
    }

    @Test
    void getAllVoiceOptions_ReturnsAllOptions() {
        when(voiceOptionRepository.findAll()).thenReturn(List.of(testVoiceOption));

        VoiceOptionListResponse result = smartphoneProductService.getAllVoiceOptions();

        assertThat(result.getVoiceOptions()).hasSize(1);
        assertThat(result.getVoiceOptions().get(0).getId()).isEqualTo("voice-5min");
        assertThat(result.getVoiceOptions().get(0).getTitle()).isEqualTo("5分通話無料");
    }

    @Test
    void getSmartphoneStock_ExistingStock_ReturnsStock() {
        SmartphoneStock stock = SmartphoneStock.builder()
                .smartphoneId("1")
                .inStock(true)
                .quantity(15)
                .estimatedDelivery("2-3営業日")
                .build();

        when(smartphoneStockRepository.findById("1")).thenReturn(Optional.of(stock));

        StockResponse result = smartphoneProductService.getSmartphoneStock("1");

        assertThat(result.getInStock()).isTrue();
        assertThat(result.getQuantity()).isEqualTo(15);
        assertThat(result.getEstimatedDelivery()).isEqualTo("2-3営業日");
    }

    @Test
    void getSmartphoneStock_NonExistingStock_ReturnsDefaultStock() {
        when(smartphoneStockRepository.findById("999")).thenReturn(Optional.empty());

        StockResponse result = smartphoneProductService.getSmartphoneStock("999");

        assertThat(result.getInStock()).isTrue();
        assertThat(result.getQuantity()).isEqualTo(10);
        assertThat(result.getEstimatedDelivery()).isEqualTo("2-3営業日");
    }

    @Test
    void calculatePrice_WithAllOptions_ReturnsCorrectTotal() {
        PriceCalculationRequest request = new PriceCalculationRequest();
        request.setDeviceId("1");
        request.setDataPlanId("plan-20gb");
        request.setVoiceOptionId("voice-5min");

        when(dataPlanRepository.findById("plan-20gb")).thenReturn(Optional.of(testDataPlan));
        when(voiceOptionRepository.findById("voice-5min")).thenReturn(Optional.of(testVoiceOption));
        when(priceCalculationRepository.save(any(PriceCalculation.class))).thenReturn(null);

        PriceCalculationResponse result = smartphoneProductService.calculatePrice(request);

        assertThat(result.getTotalPrice()).isEqualTo(3740);
        assertThat(result.getMonthlyPrice()).isEqualTo(3740);
        assertThat(result.getBreakdown()).hasSize(2);
        assertThat(result.getBreakdown().get(0).getName()).isEqualTo("20GBプラン");
        assertThat(result.getBreakdown().get(0).getPrice()).isEqualTo(2970);
        assertThat(result.getBreakdown().get(1).getName()).isEqualTo("5分通話無料");
        assertThat(result.getBreakdown().get(1).getPrice()).isEqualTo(770);
    }

    @Test
    void calculatePrice_WithOnlyDataPlan_ReturnsDataPlanPrice() {
        PriceCalculationRequest request = new PriceCalculationRequest();
        request.setDeviceId("1");
        request.setDataPlanId("plan-20gb");

        when(dataPlanRepository.findById("plan-20gb")).thenReturn(Optional.of(testDataPlan));
        when(priceCalculationRepository.save(any(PriceCalculation.class))).thenReturn(null);

        PriceCalculationResponse result = smartphoneProductService.calculatePrice(request);

        assertThat(result.getTotalPrice()).isEqualTo(2970);
        assertThat(result.getMonthlyPrice()).isEqualTo(2970);
        assertThat(result.getBreakdown()).hasSize(1);
        assertThat(result.getBreakdown().get(0).getName()).isEqualTo("20GBプラン");
    }

    @Test
    void getSmartphonesByPriceRange_ReturnsFilteredResults() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
        Page<SmartphoneProduct> page = new PageImpl<>(List.of(testSmartphone));

        when(smartphoneProductRepository.findByPriceRange(eq(50000), eq(100000), any(Pageable.class)))
                .thenReturn(page);

        SmartphoneApiResponse result = smartphoneProductService.getSmartphonesByPriceRange(50000, 100000, 0, 10);

        assertThat(result.getSmartphones()).hasSize(1);
        assertThat(result.getSmartphones().get(0).getName()).isEqualTo("iPhone 16e");
        assertThat(result.getTotalCount()).isEqualTo(1);
    }
}
