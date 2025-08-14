package com.ahamo.dummy.demo2.template.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "color_options")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColorOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "color_code", nullable = false, length = 7)
    private String colorCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smartphone_product_id", nullable = false)
    private SmartphoneProduct smartphoneProduct;
}
