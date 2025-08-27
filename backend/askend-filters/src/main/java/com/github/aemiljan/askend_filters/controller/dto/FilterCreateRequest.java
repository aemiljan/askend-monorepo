package com.github.aemiljan.askend_filters.controller.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FilterCreateRequest {
    private String name;
    private List<FilterCriteriaDTO> criteria;
}
