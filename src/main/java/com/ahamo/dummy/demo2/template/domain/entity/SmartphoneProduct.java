package com.ahamo.dummy.demo2.template.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "smartphone_products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmartphoneProduct {

    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 100)
    private String brand;

    @Column(nullable = false, length = 100)
    private String price;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(length = 500)
    private String link;

    @Column(name = "has_5g")
    private Boolean has5G;

    @Column(name = "sale_label", length = 100)
    private String saleLabel;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "smartphoneProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ColorOption> colorOptions;

    @OneToMany(mappedBy = "smartphoneProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Feature> features;

    @OneToMany(mappedBy = "smartphoneProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Specification> specifications;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
