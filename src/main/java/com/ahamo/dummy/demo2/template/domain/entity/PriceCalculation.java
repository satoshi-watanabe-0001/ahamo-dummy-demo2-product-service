package com.ahamo.dummy.demo2.template.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_calculations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "data_plan_id")
    private String dataPlanId;

    @Column(name = "voice_option_id")
    private String voiceOptionId;

    @Column(name = "oversea_option_id")
    private String overseaOptionId;

    @Column(name = "total_price", nullable = false)
    private Integer totalPrice;

    @Column(name = "monthly_price", nullable = false)
    private Integer monthlyPrice;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
