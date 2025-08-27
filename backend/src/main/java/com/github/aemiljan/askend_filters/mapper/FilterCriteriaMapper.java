package com.github.aemiljan.askend_filters.mapper;

import com.github.aemiljan.askend_filters.controller.dto.AmountFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.DateFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.FilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.TitleFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.model.entity.*;
import com.github.aemiljan.askend_filters.model.enums.FilterType;
import org.springframework.stereotype.Component;

@Component
public class FilterCriteriaMapper {

    public FilterCriteria toEntity(FilterCriteriaDTO dto, Filter filter) {
        return switch (dto) {
            case AmountFilterCriteriaDTO amountDto -> AmountFilterCriteria.builder()
                    .type(FilterType.AMOUNT)
                    .filter(filter)
                    .condition(amountDto.getCondition())
                    .value(amountDto.getValue())
                    .build();

            case TitleFilterCriteriaDTO titleDto -> TitleFilterCriteria.builder()
                    .type(FilterType.TITLE)
                    .filter(filter)
                    .condition(titleDto.getCondition())
                    .value(titleDto.getValue())
                    .build();

            case DateFilterCriteriaDTO dateDto -> DateFilterCriteria.builder()
                    .type(FilterType.DATE)
                    .filter(filter)
                    .condition(dateDto.getCondition())
                    .date(dateDto.getValue())
                    .build();

            default -> throw new IllegalArgumentException(
                    "Unknown FilterCriteriaDTO type: " + dto.getClass());
        };
    }

    public FilterCriteriaDTO toDTO(FilterCriteria entity) {
        return switch (entity) {
            case AmountFilterCriteria amount -> AmountFilterCriteriaDTO.builder()
                    .filterType(FilterType.AMOUNT)
                    .condition(amount.getCondition())
                    .value(amount.getValue())
                    .build();
            case TitleFilterCriteria title -> TitleFilterCriteriaDTO.builder()
                    .filterType(FilterType.TITLE)
                    .condition(title.getCondition())
                    .value(title.getValue())
                    .build();
            case DateFilterCriteria date -> DateFilterCriteriaDTO.builder()
                    .filterType(FilterType.DATE)
                    .condition(date.getCondition())
                    .value(date.getDate())
                    .build();
            default -> throw new IllegalArgumentException(
                    "Unsupported filter criteria type: " + entity.getClass());
        };
    }
}
