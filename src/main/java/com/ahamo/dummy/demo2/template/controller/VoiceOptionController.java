package com.ahamo.dummy.demo2.template.controller;

import com.ahamo.dummy.demo2.template.controller.dto.ApiResponse;
import com.ahamo.dummy.demo2.template.controller.dto.VoiceOptionListResponse;
import com.ahamo.dummy.demo2.template.service.SmartphoneProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/voice-options")
@RequiredArgsConstructor
@Slf4j
public class VoiceOptionController {

    private final SmartphoneProductService smartphoneProductService;

    @GetMapping
    public ResponseEntity<ApiResponse<VoiceOptionListResponse>> getAllVoiceOptions() {
        log.info("GET /api/voice-options");

        try {
            VoiceOptionListResponse response = smartphoneProductService.getAllVoiceOptions();
            return ResponseEntity.ok(ApiResponse.<VoiceOptionListResponse>builder()
                    .success(true)
                    .data(response)
                    .build());
        } catch (Exception e) {
            log.error("Error getting voice options", e);
            return ResponseEntity.internalServerError()
                    .body(ApiResponse.<VoiceOptionListResponse>builder()
                            .success(false)
                            .error("Internal server error")
                            .build());
        }
    }
}
