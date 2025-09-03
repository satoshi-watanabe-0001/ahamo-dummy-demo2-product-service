package com.ahamo.dummy.demo2.template.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "smartphone_stock")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmartphoneStock {

    @Id
    @Column(name = "smartphone_id")
    private String smartphoneId;

    @Column(name = "in_stock", nullable = false)
    private Boolean inStock;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "estimated_delivery")
    private String estimatedDelivery;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
