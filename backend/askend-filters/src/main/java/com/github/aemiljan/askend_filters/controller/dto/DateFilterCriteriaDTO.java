package com.github.aemiljan.askend_filters.controller.dto;

import com.github.aemiljan.askend_filters.model.enums.DateCondition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DateFilterCriteriaDTO extends FilterCriteriaDTO {
    private DateCondition condition;
    private LocalDate value;
}
