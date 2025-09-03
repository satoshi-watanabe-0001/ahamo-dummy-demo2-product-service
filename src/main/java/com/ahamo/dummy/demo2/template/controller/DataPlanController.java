package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.ApiResponse;
import com.ahamo.dummy.demo2.template.controller.dto.DataPlanListResponse;
import com.ahamo.dummy.demo2.template.service.SmartphoneProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/data-plans")
@RequiredArgsConstructor
@Slf4j
public class DataPlanController {

    private final SmartphoneProductService smartphoneProductService;

    @GetMapping
    public ResponseEntity<ApiResponse<DataPlanListResponse>> getAllDataPlans() {
        log.info("GET /api/data-plans");

        try {
            DataPlanListResponse response = smartphoneProductService.getAllDataPlans();
            return ResponseEntity.ok(ApiResponse.<DataPlanListResponse>builder()
                    .success(true)
                    .data(response)
                    .build());
        } catch (Exception e) {
            log.error("Error getting data plans", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<DataPlanListResponse>builder()
                            .success(false)
                            .error("Internal server error")
                            .build());
        }
    }
}
