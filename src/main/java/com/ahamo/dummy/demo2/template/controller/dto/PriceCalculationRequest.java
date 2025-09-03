package com.ahamo.dummy.demo2.template.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceCalculationRequest {
    private String deviceId;
    private String dataPlanId;
    private String voiceOptionId;
    private String overseaOptionId;
}
