package com.ahamo.dummy.demo2.template.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColorOptionDto {
    private String name;
    private String colorCode;
}
