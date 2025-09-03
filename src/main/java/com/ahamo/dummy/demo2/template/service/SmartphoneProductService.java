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

import java.util.ArrayList;
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
    private final SmartphoneStockRepository smartphoneStockRepository;
    private final PriceCalculationRepository priceCalculationRepository;

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

    public SmartphoneApiResponse searchSmartphones(String query, int page, int size) {
        log.info("Searching smartphones with query: {}, page: {}, size: {}", query, page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<SmartphoneProduct> smartphonePage = smartphoneProductRepository.findByNameContainingIgnoreCase(query, pageable);
        
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

    public SmartphoneApiResponse getSmartphonesByPriceRange(Integer minPrice, Integer maxPrice, int page, int size) {
        log.info("Getting smartphones with price range: {} - {}, page: {}, size: {}", minPrice, maxPrice, page, size);
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
        Page<SmartphoneProduct> smartphonePage = smartphoneProductRepository.findByPriceRange(minPrice, maxPrice, pageable);
        
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

    public DataPlanListResponse getAllDataPlans() {
        log.info("Getting all data plans");
        List<DataPlan> dataPlans = dataPlanRepository.findAll();
        return DataPlanListResponse.builder()
                .dataPlans(dataPlans.stream().map(this::convertToDto).collect(Collectors.toList()))
                .build();
    }

    public VoiceOptionListResponse getAllVoiceOptions() {
        log.info("Getting all voice options");
        List<VoiceOption> voiceOptions = voiceOptionRepository.findAll();
        return VoiceOptionListResponse.builder()
                .voiceOptions(voiceOptions.stream().map(this::convertToDto).collect(Collectors.toList()))
                .build();
    }

    public StockResponse getSmartphoneStock(String id) {
        log.info("Getting stock for smartphone: {}", id);
        SmartphoneStock stock = smartphoneStockRepository.findById(id)
                .orElse(SmartphoneStock.builder()
                        .smartphoneId(id)
                        .inStock(true)
                        .quantity(10)
                        .estimatedDelivery("2-3営業日")
                        .build());
        
        return StockResponse.builder()
                .inStock(stock.getInStock())
                .quantity(stock.getQuantity())
                .estimatedDelivery(stock.getEstimatedDelivery())
                .build();
    }

    public PriceCalculationResponse calculatePrice(PriceCalculationRequest request) {
        log.info("Calculating price for request: {}", request);
        
        int totalPrice = 0;
        List<PriceCalculationResponse.PriceBreakdownItem> breakdown = new ArrayList<>();
        
        if (request.getDataPlanId() != null) {
            DataPlan dataPlan = dataPlanRepository.findById(request.getDataPlanId()).orElse(null);
            if (dataPlan != null) {
                int price = Integer.parseInt(dataPlan.getPrice());
                totalPrice += price;
                breakdown.add(PriceCalculationResponse.PriceBreakdownItem.builder()
                        .name(dataPlan.getTitle())
                        .price(price)
                        .build());
            }
        }
        
        if (request.getVoiceOptionId() != null) {
            VoiceOption voiceOption = voiceOptionRepository.findById(request.getVoiceOptionId()).orElse(null);
            if (voiceOption != null) {
                int price = Integer.parseInt(voiceOption.getPrice());
                totalPrice += price;
                breakdown.add(PriceCalculationResponse.PriceBreakdownItem.builder()
                        .name(voiceOption.getTitle())
                        .price(price)
                        .build());
            }
        }
        
        if (request.getOverseaOptionId() != null) {
            OverseaCallingOption overseaOption = overseaCallingOptionRepository.findById(request.getOverseaOptionId()).orElse(null);
            if (overseaOption != null) {
                int price = Integer.parseInt(overseaOption.getPrice());
                totalPrice += price;
                breakdown.add(PriceCalculationResponse.PriceBreakdownItem.builder()
                        .name(overseaOption.getTitle())
                        .price(price)
                        .build());
            }
        }
        
        PriceCalculation calculation = PriceCalculation.builder()
                .deviceId(request.getDeviceId())
                .dataPlanId(request.getDataPlanId())
                .voiceOptionId(request.getVoiceOptionId())
                .overseaOptionId(request.getOverseaOptionId())
                .totalPrice(totalPrice)
                .monthlyPrice(totalPrice)
                .build();
        
        priceCalculationRepository.save(calculation);
        
        return PriceCalculationResponse.builder()
                .totalPrice(totalPrice)
                .monthlyPrice(totalPrice)
                .breakdown(breakdown)
                .build();
    }
}
