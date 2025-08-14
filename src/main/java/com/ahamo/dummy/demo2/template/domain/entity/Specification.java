package com.ahamo.dummy.demo2.template.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "specifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Specification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "specification_text", nullable = false, length = 500)
    private String specificationText;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "smartphone_product_id", nullable = false)
    private SmartphoneProduct smartphoneProduct;
}
