package com.ahamo.dummy.demo2.template.integration;

import com.ahamo.dummy.demo2.template.domain.entity.SmartphoneProduct;
import com.ahamo.dummy.demo2.template.repository.SmartphoneProductRepository;
import com.ahamo.dummy.demo2.template.repository.DataPlanRepository;
import com.ahamo.dummy.demo2.template.repository.VoiceOptionRepository;
import com.ahamo.dummy.demo2.template.repository.OverseaCallingOptionRepository;
import com.ahamo.dummy.demo2.template.service.SmartphoneProductService;
import com.ahamo.dummy.demo2.template.controller.dto.SmartphoneOptionsDto;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ActiveProfiles("integration")
@Tag("integration")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PostgreSQLIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("ahamo_dummy_demo2_test")
            .withUsername("ahamo_user")
            .withPassword("ahamo_password");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.flyway.enabled", () -> "true");
        registry.add("spring.flyway.locations", () -> "classpath:db/migration");
        registry.add("spring.flyway.baseline-on-migrate", () -> "true");
    }

    @Autowired
    private SmartphoneProductService smartphoneProductService;

    @Autowired
    private SmartphoneProductRepository smartphoneProductRepository;
    
    @Autowired
    private DataPlanRepository dataPlanRepository;
    
    @Autowired
    private VoiceOptionRepository voiceOptionRepository;
    
    @Autowired
    private OverseaCallingOptionRepository overseaCallingOptionRepository;

    @Test
    void getSmartphones_WithPostgreSQLDatabase_ReturnsActualData() throws Exception {
        long startTime = System.currentTimeMillis();
        
        long totalCount = smartphoneProductRepository.count();
        
        long responseTime = System.currentTimeMillis() - startTime;
        
        assertThat(responseTime).isLessThanOrEqualTo(500L);
        assertThat(totalCount).isGreaterThan(0);
        
        List<SmartphoneProduct> smartphones = smartphoneProductRepository.findAll();
        assertThat(smartphones).isNotEmpty();
        assertThat(smartphones.size()).isEqualTo(9); // Based on V2__Insert_initial_data.sql
        
        SmartphoneProduct firstSmartphone = smartphones.get(0);
        assertThat(firstSmartphone.getId()).isNotBlank();
        assertThat(firstSmartphone.getName()).isNotBlank();
        assertThat(firstSmartphone.getBrand()).isNotBlank();
        assertThat(firstSmartphone.getPrice()).isNotBlank();
    }

    @Test
    void getSmartphones_WithBrandFilter_PostgreSQLIntegration() throws Exception {
        long startTime = System.currentTimeMillis();
        
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<SmartphoneProduct> applePhones = smartphoneProductRepository.findByBrandWithPagination("Apple", pageRequest);
        
        long responseTime = System.currentTimeMillis() - startTime;
        
        assertThat(responseTime).isLessThanOrEqualTo(500L);
        assertThat(applePhones).isNotNull();
        assertThat(applePhones.getContent().size()).isEqualTo(5);
        
        assertThat(applePhones.getContent()).allMatch(
                smartphone -> "Apple".equals(smartphone.getBrand())
        );
    }

    @Test
    void getSmartphones_WithPagination_PostgreSQLIntegration() throws Exception {
        long startTime = System.currentTimeMillis();
        
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<SmartphoneProduct> result = smartphoneProductRepository.findAll(pageRequest);
        
        long responseTime = System.currentTimeMillis() - startTime;
        
        assertThat(responseTime).isLessThanOrEqualTo(500L);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSizeLessThanOrEqualTo(5);
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getTotalElements()).isEqualTo(9);
    }

    @Test
    void getSmartphoneById_WithPostgreSQLDatabase_ReturnsCompleteData() throws Exception {
        long startTime = System.currentTimeMillis();
        
        Optional<SmartphoneProduct> result = smartphoneProductRepository.findById("1");
        
        long responseTime = System.currentTimeMillis() - startTime;
        
        assertThat(responseTime).isLessThanOrEqualTo(500L);
        assertThat(result).isPresent();
        
        SmartphoneProduct smartphone = result.get();
        assertThat(smartphone.getId()).isEqualTo("1");
        assertThat(smartphone.getName()).isEqualTo("iPhone 16e");
        assertThat(smartphone.getBrand()).isEqualTo("Apple");
        assertThat(smartphone.getPrice()).isEqualTo("43,670円〜");
    }

    @Test
    void getSmartphoneOptions_WithPostgreSQLDatabase_ReturnsAllOptions() throws Exception {
        long startTime = System.currentTimeMillis();
        
        SmartphoneOptionsDto result = smartphoneProductService.getSmartphoneOptions("test-device");

        long responseTime = System.currentTimeMillis() - startTime;
        
        assertThat(responseTime).isLessThanOrEqualTo(500L);
        assertThat(result).isNotNull();
        assertThat(result.getDataPlans()).isNotEmpty();
        assertThat(result.getVoiceOptions()).isNotEmpty();
        assertThat(result.getOverseaCallingOptions()).isNotEmpty();
    }

    @Test
    void postgreSQLDatabase_ConnectivityAndDataIntegrity_WorksCorrectly() throws Exception {
        long totalCount = smartphoneProductRepository.count();
        assertThat(totalCount).isEqualTo(9);

        PageRequest samsungPageRequest = PageRequest.of(0, 10);
        Page<SmartphoneProduct> samsungPhones = smartphoneProductRepository.findByBrandWithPagination("Samsung", samsungPageRequest);
        assertThat(samsungPhones.getContent()).hasSize(1);
        assertThat(samsungPhones.getContent().get(0).getName()).isEqualTo("Galaxy S24");

        long dataPlanCount = dataPlanRepository.count();
        assertThat(dataPlanCount).isEqualTo(2);
        
        long voiceOptionCount = voiceOptionRepository.count();
        assertThat(voiceOptionCount).isEqualTo(2);
        
        long overseaCallingOptionCount = overseaCallingOptionRepository.count();
        assertThat(overseaCallingOptionCount).isEqualTo(2);
    }
}
