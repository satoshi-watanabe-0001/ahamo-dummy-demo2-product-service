package com.ahamo.dummy.demo2.template.repository;

import com.ahamo.dummy.demo2.template.domain.entity.SmartphoneProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class SmartphoneProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SmartphoneProductRepository smartphoneProductRepository;

    @Test
    void findByBrandWithPagination_WithBrandFilter_ReturnsFilteredResults() {
        SmartphoneProduct iphone = SmartphoneProduct.builder()
                .id("1")
                .name("iPhone 16e")
                .brand("Apple")
                .price("43,670円〜")
                .has5G(true)
                .build();

        SmartphoneProduct galaxy = SmartphoneProduct.builder()
                .id("2")
                .name("Galaxy S24")
                .brand("Samsung")
                .price("124,800円〜")
                .has5G(true)
                .build();

        entityManager.persistAndFlush(iphone);
        entityManager.persistAndFlush(galaxy);

        Page<SmartphoneProduct> result = smartphoneProductRepository
                .findByBrandWithPagination("Apple", PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getBrand()).isEqualTo("Apple");
        assertThat(result.getContent().get(0).getName()).isEqualTo("iPhone 16e");
    }

    @Test
    void findByBrandWithPagination_WithoutBrandFilter_ReturnsAllResults() {
        SmartphoneProduct iphone = SmartphoneProduct.builder()
                .id("1")
                .name("iPhone 16e")
                .brand("Apple")
                .price("43,670円〜")
                .has5G(true)
                .build();

        SmartphoneProduct galaxy = SmartphoneProduct.builder()
                .id("2")
                .name("Galaxy S24")
                .brand("Samsung")
                .price("124,800円〜")
                .has5G(true)
                .build();

        entityManager.persistAndFlush(iphone);
        entityManager.persistAndFlush(galaxy);

        Page<SmartphoneProduct> result = smartphoneProductRepository
                .findByBrandWithPagination(null, PageRequest.of(0, 10));

        assertThat(result.getContent()).hasSize(2);
    }

    @Test
    void findByIdWithColorOptions_ExistingId_ReturnsSmartphoneWithColorOptions() {
        SmartphoneProduct smartphone = SmartphoneProduct.builder()
                .id("1")
                .name("iPhone 16e")
                .brand("Apple")
                .price("43,670円〜")
                .has5G(true)
                .build();

        entityManager.persistAndFlush(smartphone);

        SmartphoneProduct result = smartphoneProductRepository.findByIdWithColorOptions("1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getName()).isEqualTo("iPhone 16e");
    }

    @Test
    void findByIdWithColorOptions_NonExistingId_ReturnsNull() {
        SmartphoneProduct result = smartphoneProductRepository.findByIdWithColorOptions("999");

        assertThat(result).isNull();
    }
}
