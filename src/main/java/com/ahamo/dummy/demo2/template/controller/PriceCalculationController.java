package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.ApiResponse;
import com.ahamo.dummy.demo2.template.controller.dto.PriceCalculationRequest;
import com.ahamo.dummy.demo2.template.controller.dto.PriceCalculationResponse;
import com.ahamo.dummy.demo2.template.service.SmartphoneProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class PriceCalculationController {

    private final SmartphoneProductService smartphoneProductService;

    @PostMapping("/calculate-price")
    public ResponseEntity<ApiResponse<PriceCalculationResponse>> calculatePrice(@RequestBody PriceCalculationRequest request) {
        log.info("POST /api/calculate-price - request: {}", request);

        try {
            PriceCalculationResponse response = smartphoneProductService.calculatePrice(request);

            return ResponseEntity.ok(ApiResponse.<PriceCalculationResponse>builder()
                    .success(true)
                    .data(response)
                    .build());
        } catch (Exception e) {
            log.error("Error calculating price", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<PriceCalculationResponse>builder()
                            .success(false)
                            .error("Internal server error")
                            .build());
        }
    }
}
