package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.domain.entity.SmartphoneStock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SmartphoneStockRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SmartphoneStockRepository smartphoneStockRepository;

    @Test
    void findById_ExistingStock_ReturnsStock() {
        SmartphoneStock stock = SmartphoneStock.builder()
                .smartphoneId("test-1")
                .inStock(true)
                .quantity(15)
                .estimatedDelivery("2-3営業日")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        entityManager.persistAndFlush(stock);

        Optional<SmartphoneStock> result = smartphoneStockRepository.findById("test-1");

        assertThat(result).isPresent();
        assertThat(result.get().getInStock()).isTrue();
        assertThat(result.get().getQuantity()).isEqualTo(15);
        assertThat(result.get().getEstimatedDelivery()).isEqualTo("2-3営業日");
    }

    @Test
    void findById_NonExistingStock_ReturnsEmpty() {
        Optional<SmartphoneStock> result = smartphoneStockRepository.findById("non-existing");

        assertThat(result).isEmpty();
    }

    @Test
    void save_NewStock_SavesSuccessfully() {
        SmartphoneStock stock = SmartphoneStock.builder()
                .smartphoneId("test-2")
                .inStock(false)
                .quantity(0)
                .estimatedDelivery("入荷待ち")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        SmartphoneStock saved = smartphoneStockRepository.save(stock);

        assertThat(saved.getSmartphoneId()).isEqualTo("test-2");
        assertThat(saved.getInStock()).isFalse();
        assertThat(saved.getQuantity()).isEqualTo(0);
        assertThat(saved.getEstimatedDelivery()).isEqualTo("入荷待ち");
        assertThat(saved.getCreatedAt()).isNotNull();
        assertThat(saved.getUpdatedAt()).isNotNull();
    }
}
