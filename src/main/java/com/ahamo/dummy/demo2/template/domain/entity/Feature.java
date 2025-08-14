package com.ahamo.dummy.demo2.template.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "features")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "feature_text", nullable = false, length = 500)
    private String featureText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smartphone_product_id", nullable = false)
    private SmartphoneProduct smartphoneProduct;
}
