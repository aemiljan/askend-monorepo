package com.github.aemiljan.askend_filters.controller.dto;

import com.github.aemiljan.askend_filters.model.enums.TitleCondition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TitleFilterCriteriaDTO extends FilterCriteriaDTO {
    private TitleCondition condition;
    private String value;
}
