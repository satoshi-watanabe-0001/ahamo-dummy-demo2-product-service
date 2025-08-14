package com.ahamo.dummy.demo2.template.service;

import com.ahamo.dummy.demo2.template.controller.dto.*;
import com.ahamo.dummy.demo2.template.domain.entity.*;
import com.ahamo.dummy.demo2.template.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SmartphoneProductService {

    private final SmartphoneProductRepository smartphoneProductRepository;
    private final DataPlanRepository dataPlanRepository;
    private final VoiceOptionRepository voiceOptionRepository;
    private final OverseaCallingOptionRepository overseaCallingOptionRepository;

    public SmartphoneApiResponse getSmartphones(String brand, int page, int size) {
        log.info("Getting smartphones with brand: {}, page: {}, size: {}", brand, page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<SmartphoneProduct> smartphonePage = smartphoneProductRepository.findByBrandWithPagination(brand, pageable);
        
        List<SmartphoneProductDto> smartphones = smartphonePage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        
        return SmartphoneApiResponse.builder()
                .smartphones(smartphones)
                .totalCount((int) smartphonePage.getTotalElements())
                .currentPage(page)
                .totalPages(smartphonePage.getTotalPages())
                .hasNext(smartphonePage.hasNext())
                .hasPrevious(smartphonePage.hasPrevious())
                .build();
    }

    public SmartphoneProductDto getSmartphoneById(String id) {
        log.info("Getting smartphone by id: {}", id);
        
        SmartphoneProduct smartphone = smartphoneProductRepository.findById(id).orElse(null);
        if (smartphone == null) {
            return null;
        }
        
        SmartphoneProduct withColorOptions = smartphoneProductRepository.findByIdWithColorOptions(id);
        SmartphoneProduct withFeatures = smartphoneProductRepository.findByIdWithFeatures(id);
        SmartphoneProduct withSpecifications = smartphoneProductRepository.findByIdWithSpecifications(id);
        
        if (withColorOptions != null) {
            smartphone.setColorOptions(withColorOptions.getColorOptions());
        }
        if (withFeatures != null) {
            smartphone.setFeatures(withFeatures.getFeatures());
        }
        if (withSpecifications != null) {
            smartphone.setSpecifications(withSpecifications.getSpecifications());
        }
        
        return convertToDto(smartphone);
    }

    public SmartphoneOptionsDto getSmartphoneOptions(String deviceId) {
        log.info("Getting smartphone options for deviceId: {}", deviceId);
        
        List<DataPlan> dataPlans = dataPlanRepository.findAll();
        List<VoiceOption> voiceOptions = voiceOptionRepository.findAll();
        List<OverseaCallingOption> overseaCallingOptions = overseaCallingOptionRepository.findAll();
        
        return SmartphoneOptionsDto.builder()
                .dataPlans(dataPlans.stream().map(this::convertToDto).collect(Collectors.toList()))
                .voiceOptions(voiceOptions.stream().map(this::convertToDto).collect(Collectors.toList()))
                .overseaCallingOptions(overseaCallingOptions.stream().map(this::convertToDto).collect(Collectors.toList()))
                .build();
    }

    private SmartphoneProductDto convertToDto(SmartphoneProduct smartphone) {
        List<ColorOptionDto> colorOptions = smartphone.getColorOptions() != null ?
                smartphone.getColorOptions().stream()
                        .map(co -> ColorOptionDto.builder()
                                .name(co.getName())
                                .colorCode(co.getColorCode())
                                .build())
                        .collect(Collectors.toList()) : null;

        List<String> features = smartphone.getFeatures() != null ?
                smartphone.getFeatures().stream()
                        .map(Feature::getFeatureText)
                        .collect(Collectors.toList()) : null;

        List<String> specifications = smartphone.getSpecifications() != null ?
                smartphone.getSpecifications().stream()
                        .map(Specification::getSpecificationText)
                        .collect(Collectors.toList()) : null;

        return SmartphoneProductDto.builder()
                .id(smartphone.getId())
                .name(smartphone.getName())
                .brand(smartphone.getBrand())
                .price(smartphone.getPrice())
                .imageUrl(smartphone.getImageUrl())
                .features(features)
                .link(smartphone.getLink())
                .colorOptions(colorOptions)
                .has5G(smartphone.getHas5G())
                .saleLabel(smartphone.getSaleLabel())
                .description(smartphone.getDescription())
                .specifications(specifications)
                .build();
    }

    private DataPlanDto convertToDto(DataPlan dataPlan) {
        return DataPlanDto.builder()
                .id(dataPlan.getId())
                .title(dataPlan.getTitle())
                .subtitle(dataPlan.getSubtitle())
                .price(dataPlan.getPrice())
                .description(dataPlan.getDescription())
                .build();
    }

    private VoiceOptionDto convertToDto(VoiceOption voiceOption) {
        return VoiceOptionDto.builder()
                .id(voiceOption.getId())
                .title(voiceOption.getTitle())
                .description(voiceOption.getDescription())
                .price(voiceOption.getPrice())
                .build();
    }

    private OverseaCallingOptionDto convertToDto(OverseaCallingOption overseaCallingOption) {
        return OverseaCallingOptionDto.builder()
                .id(overseaCallingOption.getId())
                .title(overseaCallingOption.getTitle())
                .description(overseaCallingOption.getDescription())
                .price(overseaCallingOption.getPrice())
                .build();
    }
}
