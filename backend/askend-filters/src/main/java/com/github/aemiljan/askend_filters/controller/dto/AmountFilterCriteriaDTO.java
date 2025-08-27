package com.github.aemiljan.askend_filters.controller.dto;

import com.github.aemiljan.askend_filters.model.enums.AmountCondition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AmountFilterCriteriaDTO extends FilterCriteriaDTO {
    private AmountCondition condition;
    private Long value;
}
