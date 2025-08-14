package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.ApiResponse;
import com.ahamo.dummy.demo2.template.controller.dto.SmartphoneApiResponse;
import com.ahamo.dummy.demo2.template.controller.dto.SmartphoneOptionsDto;
import com.ahamo.dummy.demo2.template.controller.dto.SmartphoneProductDto;
import com.ahamo.dummy.demo2.template.service.SmartphoneProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/smartphones")
@RequiredArgsConstructor
@Slf4j
public class SmartphoneProductController {

    private final SmartphoneProductService smartphoneProductService;

    @GetMapping
    public ResponseEntity<ApiResponse<SmartphoneApiResponse>> getSmartphones(
            @RequestParam(required = false) String brand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        log.info("GET /api/v1/smartphones - brand: {}, page: {}, size: {}", brand, page, size);
        
        try {
            SmartphoneApiResponse response = smartphoneProductService.getSmartphones(brand, page, size);
            
            return ResponseEntity.ok(ApiResponse.<SmartphoneApiResponse>builder()
                    .success(true)
                    .data(response)
                    .build());
        } catch (Exception e) {
            log.error("Error getting smartphones", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<SmartphoneApiResponse>builder()
                            .success(false)
                            .error("Internal server error")
                            .build());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SmartphoneProductDto>> getSmartphoneById(@PathVariable String id) {
        log.info("GET /api/v1/smartphones/{}", id);
        
        try {
            SmartphoneProductDto smartphone = smartphoneProductService.getSmartphoneById(id);
            
            if (smartphone == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(ApiResponse.<SmartphoneProductDto>builder()
                    .success(true)
                    .data(smartphone)
                    .build());
        } catch (Exception e) {
            log.error("Error getting smartphone by id: {}", id, e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<SmartphoneProductDto>builder()
                            .success(false)
                            .error("Internal server error")
                            .build());
        }
    }

    @GetMapping("/{deviceId}/options")
    public ResponseEntity<ApiResponse<SmartphoneOptionsDto>> getSmartphoneOptions(@PathVariable String deviceId) {
        log.info("GET /api/v1/smartphones/{}/options", deviceId);
        
        try {
            SmartphoneOptionsDto options = smartphoneProductService.getSmartphoneOptions(deviceId);
            
            return ResponseEntity.ok(ApiResponse.<SmartphoneOptionsDto>builder()
                    .success(true)
                    .data(options)
                    .build());
        } catch (Exception e) {
            log.error("Error getting smartphone options for deviceId: {}", deviceId, e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<SmartphoneOptionsDto>builder()
                            .success(false)
                            .error("Internal server error")
                            .build());
        }
    }
}
