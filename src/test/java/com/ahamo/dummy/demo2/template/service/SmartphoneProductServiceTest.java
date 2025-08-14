package com.ahamo.dummy.demo2.template.service;

import com.ahamo.dummy.demo2.template.controller.dto.SmartphoneApiResponse;
import com.ahamo.dummy.demo2.template.controller.dto.SmartphoneOptionsDto;
import com.ahamo.dummy.demo2.template.controller.dto.SmartphoneProductDto;
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
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SmartphoneProductServiceTest {

    @Mock
    private SmartphoneProductRepository smartphoneProductRepository;

    @Mock
    private DataPlanRepository dataPlanRepository;

    @Mock
    private VoiceOptionRepository voiceOptionRepository;

    @Mock
    private OverseaCallingOptionRepository overseaCallingOptionRepository;

    @InjectMocks
    private SmartphoneProductService smartphoneProductService;

    private SmartphoneProduct testSmartphone;
    private ColorOption testColorOption;
    private Feature testFeature;
    private Specification testSpecification;

    @BeforeEach
    void setUp() {
        testSmartphone = SmartphoneProduct.builder()
                .id("1")
                .name("iPhone 16e")
                .brand("Apple")
                .price("43,670円〜")
                .imageUrl("https://example.com/image.jpg")
                .link("/smartphones/iphone16e")
                .has5G(true)
                .saleLabel("SALE!")
                .description("Test description")
                .build();

        testColorOption = ColorOption.builder()
                .id(1L)
                .name("ホワイト")
                .colorCode("#FFFFFF")
                .smartphoneProduct(testSmartphone)
                .build();

        testFeature = Feature.builder()
                .id(1L)
                .featureText("いつでもカエドキプログラム適用時")
                .smartphoneProduct(testSmartphone)
                .build();

        testSpecification = Specification.builder()
                .id(1L)
                .specificationText("A16 Bionicチップ")
                .smartphoneProduct(testSmartphone)
                .build();

        testSmartphone.setColorOptions(Set.of(testColorOption));
        testSmartphone.setFeatures(Set.of(testFeature));
        testSmartphone.setSpecifications(Set.of(testSpecification));
    }

    @Test
    void getSmartphones_WithoutBrandFilter_ReturnsAllSmartphones() {
        Page<SmartphoneProduct> page = new PageImpl<>(List.of(testSmartphone));
        when(smartphoneProductRepository.findByBrandWithPagination(eq(null), any(Pageable.class)))
                .thenReturn(page);

        SmartphoneApiResponse result = smartphoneProductService.getSmartphones(null, 0, 10);

        assertThat(result.getSmartphones()).hasSize(1);
        assertThat(result.getSmartphones().get(0).getId()).isEqualTo("1");
        assertThat(result.getSmartphones().get(0).getName()).isEqualTo("iPhone 16e");
        assertThat(result.getTotalCount()).isEqualTo(1);
        assertThat(result.getCurrentPage()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.isHasNext()).isFalse();
        assertThat(result.isHasPrevious()).isFalse();
    }

    @Test
    void getSmartphones_WithBrandFilter_ReturnsFilteredSmartphones() {
        Page<SmartphoneProduct> page = new PageImpl<>(List.of(testSmartphone));
        when(smartphoneProductRepository.findByBrandWithPagination(eq("Apple"), any(Pageable.class)))
                .thenReturn(page);

        SmartphoneApiResponse result = smartphoneProductService.getSmartphones("Apple", 0, 10);

        assertThat(result.getSmartphones()).hasSize(1);
        assertThat(result.getSmartphones().get(0).getBrand()).isEqualTo("Apple");
    }

    @Test
    void getSmartphoneById_ExistingId_ReturnsSmartphone() {
        when(smartphoneProductRepository.findById("1"))
                .thenReturn(Optional.of(testSmartphone));
        when(smartphoneProductRepository.findByIdWithColorOptions("1"))
                .thenReturn(testSmartphone);
        when(smartphoneProductRepository.findByIdWithFeatures("1"))
                .thenReturn(testSmartphone);
        when(smartphoneProductRepository.findByIdWithSpecifications("1"))
                .thenReturn(testSmartphone);

        SmartphoneProductDto result = smartphoneProductService.getSmartphoneById("1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getName()).isEqualTo("iPhone 16e");
        assertThat(result.getBrand()).isEqualTo("Apple");
        assertThat(result.getColorOptions()).hasSize(1);
        assertThat(result.getColorOptions().get(0).getName()).isEqualTo("ホワイト");
        assertThat(result.getFeatures()).hasSize(1);
        assertThat(result.getFeatures().get(0)).isEqualTo("いつでもカエドキプログラム適用時");
        assertThat(result.getSpecifications()).hasSize(1);
        assertThat(result.getSpecifications().get(0)).isEqualTo("A16 Bionicチップ");
    }

    @Test
    void getSmartphoneById_NonExistingId_ReturnsNull() {
        when(smartphoneProductRepository.findById("999"))
                .thenReturn(Optional.empty());

        SmartphoneProductDto result = smartphoneProductService.getSmartphoneById("999");

        assertThat(result).isNull();
    }

    @Test
    void getSmartphoneOptions_ReturnsAllOptions() {
        DataPlan dataPlan = DataPlan.builder()
                .id("30gb")
                .title("30GB")
                .subtitle("2,970円/月")
                .price("2970")
                .description("データ通信量")
                .build();

        VoiceOption voiceOption = VoiceOption.builder()
                .id("none")
                .title("申し込まない")
                .description("")
                .price("0")
                .build();

        OverseaCallingOption overseaOption = OverseaCallingOption.builder()
                .id("none")
                .title("申し込まない")
                .description("1分/回無料")
                .price("0")
                .build();

        when(dataPlanRepository.findAll()).thenReturn(List.of(dataPlan));
        when(voiceOptionRepository.findAll()).thenReturn(List.of(voiceOption));
        when(overseaCallingOptionRepository.findAll()).thenReturn(List.of(overseaOption));

        SmartphoneOptionsDto result = smartphoneProductService.getSmartphoneOptions("1");

        assertThat(result.getDataPlans()).hasSize(1);
        assertThat(result.getDataPlans().get(0).getId()).isEqualTo("30gb");
        assertThat(result.getVoiceOptions()).hasSize(1);
        assertThat(result.getVoiceOptions().get(0).getId()).isEqualTo("none");
        assertThat(result.getOverseaCallingOptions()).hasSize(1);
        assertThat(result.getOverseaCallingOptions().get(0).getId()).isEqualTo("none");
    }
}
