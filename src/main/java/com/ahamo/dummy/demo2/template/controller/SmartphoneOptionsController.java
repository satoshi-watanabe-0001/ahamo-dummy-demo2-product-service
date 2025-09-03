package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.ApiResponse;
import com.ahamo.dummy.demo2.template.controller.dto.SmartphoneOptionsDto;
import com.ahamo.dummy.demo2.template.service.SmartphoneProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/smartphone-options")
@RequiredArgsConstructor
@Slf4j
public class SmartphoneOptionsController {

    private final SmartphoneProductService smartphoneProductService;

    @GetMapping("/{deviceId}")
    public ResponseEntity<ApiResponse<SmartphoneOptionsDto>> getSmartphoneOptions(@PathVariable String deviceId) {
        log.info("GET /api/smartphone-options/{}", deviceId);

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
