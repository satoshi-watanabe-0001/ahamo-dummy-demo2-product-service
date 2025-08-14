package com.ahamo.dummy.demo2.template.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SmartphoneOptionsDto {
    private List<DataPlanDto> dataPlans;
    private List<VoiceOptionDto> voiceOptions;
    private List<OverseaCallingOptionDto> overseaCallingOptions;
}
