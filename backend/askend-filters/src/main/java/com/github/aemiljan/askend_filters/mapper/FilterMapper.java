package com.github.aemiljan.askend_filters.mapper;

import com.github.aemiljan.askend_filters.controller.dto.FilterDTO;
import com.github.aemiljan.askend_filters.model.entity.Filter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FilterMapper {

    private final FilterCriteriaMapper filterCriteriaMapper;

    public FilterMapper(FilterCriteriaMapper filterCriteriaMapper) {
        this.filterCriteriaMapper = filterCriteriaMapper;
    }

    public FilterDTO toDTO(Filter filter) {
        if (filter == null) {
            return null;
        }

        return FilterDTO.builder()
                .id(filter.getId())
                .name(filter.getName())
                .criteria(
                        filter.getCriteria() == null ? List.of() :
                                filter.getCriteria().stream()
                                        .map(filterCriteriaMapper::toDTO)
                                        .toList()
                )
                .build();
    }
}
