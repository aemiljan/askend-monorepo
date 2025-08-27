package com.github.aemiljan.askend_filters.controller.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.aemiljan.askend_filters.model.enums.FilterType;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "filterType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AmountFilterCriteriaDTO.class, name = "AMOUNT"),
        @JsonSubTypes.Type(value = TitleFilterCriteriaDTO.class, name = "TITLE"),
        @JsonSubTypes.Type(value = DateFilterCriteriaDTO.class, name = "DATE")
})
public abstract class FilterCriteriaDTO {
    private FilterType filterType;
}
