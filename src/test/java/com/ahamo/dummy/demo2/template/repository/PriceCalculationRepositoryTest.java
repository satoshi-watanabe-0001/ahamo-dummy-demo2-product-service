package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.domain.entity.PriceCalculation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class PriceCalculationRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PriceCalculationRepository priceCalculationRepository;

    @Test
    void save_NewCalculation_SavesSuccessfully() {
        PriceCalculation calculation = PriceCalculation.builder()
                .deviceId("iphone-15")
                .dataPlanId("plan-20gb")
                .voiceOptionId("voice-5min")
                .overseaOptionId("oversea-unlimited")
                .totalPrice(3740)
                .monthlyPrice(3740)
                .createdAt(LocalDateTime.now())
                .build();

        PriceCalculation saved = priceCalculationRepository.save(calculation);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getDeviceId()).isEqualTo("iphone-15");
        assertThat(saved.getDataPlanId()).isEqualTo("plan-20gb");
        assertThat(saved.getVoiceOptionId()).isEqualTo("voice-5min");
        assertThat(saved.getOverseaOptionId()).isEqualTo("oversea-unlimited");
        assertThat(saved.getTotalPrice()).isEqualTo(3740);
        assertThat(saved.getMonthlyPrice()).isEqualTo(3740);
        assertThat(saved.getCreatedAt()).isNotNull();
    }

    @Test
    void findAll_ReturnsAllCalculations() {
        PriceCalculation calculation1 = PriceCalculation.builder()
                .deviceId("iphone-15")
                .dataPlanId("plan-20gb")
                .totalPrice(2970)
                .monthlyPrice(2970)
                .createdAt(LocalDateTime.now())
                .build();

        PriceCalculation calculation2 = PriceCalculation.builder()
                .deviceId("galaxy-s24")
                .dataPlanId("plan-30gb")
                .totalPrice(3740)
                .monthlyPrice(3740)
                .createdAt(LocalDateTime.now())
                .build();

        entityManager.persistAndFlush(calculation1);
        entityManager.persistAndFlush(calculation2);

        long count = priceCalculationRepository.count();

        assertThat(count).isEqualTo(2);
    }
}
