package com.github.aemiljan.askend_filters.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FilterDTO {
    private Long id;
    private String name;
    private List<FilterCriteriaDTO> criteria;
}
