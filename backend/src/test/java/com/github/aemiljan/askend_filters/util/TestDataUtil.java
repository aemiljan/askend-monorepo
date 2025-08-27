package com.github.aemiljan.askend_filters.util;

import com.github.aemiljan.askend_filters.controller.dto.AmountFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.FilterCreateRequest;
import com.github.aemiljan.askend_filters.controller.dto.FilterDTO;
import com.github.aemiljan.askend_filters.model.enums.AmountCondition;
import com.github.aemiljan.askend_filters.model.enums.FilterType;
import com.github.aemiljan.askend_filters.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDataUtil {
    @Autowired
    private FilterService filterService;

    public FilterDTO createSampleFilter() {
        AmountFilterCriteriaDTO amountDTO = AmountFilterCriteriaDTO.builder()
                .filterType(FilterType.AMOUNT)
                .condition(AmountCondition.GREATER_OR_EQUAL)
                .value(1000L)
                .build();

        FilterCreateRequest request = FilterCreateRequest.builder()
                .name("Sample Filter")
                .criteria(List.of(amountDTO))
                .build();

        return filterService.createFilter(request);
    }
}
