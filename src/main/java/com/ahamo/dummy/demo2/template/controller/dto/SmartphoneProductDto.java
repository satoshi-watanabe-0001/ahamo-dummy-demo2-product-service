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
public class SmartphoneProductDto {
    private String id;
    private String name;
    private String brand;
    private String price;
    private String imageUrl;
    private List<String> features;
    private String link;
    private List<ColorOptionDto> colorOptions;
    private Boolean has5G;
    private String saleLabel;
    private String description;
    private List<String> specifications;
}
