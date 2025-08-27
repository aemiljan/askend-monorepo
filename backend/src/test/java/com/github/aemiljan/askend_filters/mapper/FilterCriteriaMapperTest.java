package com.github.aemiljan.askend_filters.mapper;

import com.github.aemiljan.askend_filters.controller.dto.AmountFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.DateFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.FilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.TitleFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.model.entity.*;
import com.github.aemiljan.askend_filters.model.enums.AmountCondition;
import com.github.aemiljan.askend_filters.model.enums.DateCondition;
import com.github.aemiljan.askend_filters.model.enums.FilterType;
import com.github.aemiljan.askend_filters.model.enums.TitleCondition;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilterCriteriaMapperTest {

    private final FilterCriteriaMapper mapper = new FilterCriteriaMapper();

    @Test
    void shouldMapAmountDtoToEntity() {
        Filter filter = Filter.builder().id(100L).name("Parent Filter").build();

        AmountFilterCriteriaDTO dto = AmountFilterCriteriaDTO.builder()
                .filterType(FilterType.AMOUNT)
                .condition(AmountCondition.GREATER_OR_EQUAL)
                .value(1000L)
                .build();

        FilterCriteria entity = mapper.toEntity(dto, filter);

        assertNotNull(entity);
        assertTrue(entity instanceof AmountFilterCriteria);
        AmountFilterCriteria amount = (AmountFilterCriteria) entity;
        assertEquals(dto.getCondition(), amount.getCondition());
        assertEquals(dto.getValue(), amount.getValue());
        assertEquals(filter, amount.getFilter());
    }

    @Test
    void shouldMapTitleDtoToEntity() {
        Filter filter = Filter.builder().id(200L).name("Parent").build();

        TitleFilterCriteriaDTO dto = TitleFilterCriteriaDTO.builder()
                .filterType(FilterType.TITLE)
                .condition(TitleCondition.CONTAINS)
                .value("Important")
                .build();

        FilterCriteria entity = mapper.toEntity(dto, filter);

        assertNotNull(entity);
        assertTrue(entity instanceof TitleFilterCriteria);
        TitleFilterCriteria title = (TitleFilterCriteria) entity;
        assertEquals(dto.getCondition(), title.getCondition());
        assertEquals(dto.getValue(), title.getValue());
        assertEquals(filter, title.getFilter());
    }

    @Test
    void shouldMapDateDtoToEntity() {
        Filter filter = Filter.builder().id(300L).name("Date Parent").build();

        LocalDate date = LocalDate.of(2025, 1, 1);
        DateFilterCriteriaDTO dto = DateFilterCriteriaDTO.builder()
                .filterType(FilterType.DATE)
                .condition(DateCondition.AFTER)
                .value(date)
                .build();

        FilterCriteria entity = mapper.toEntity(dto, filter);

        assertNotNull(entity);
        assertTrue(entity instanceof DateFilterCriteria);
        DateFilterCriteria dateCriteria = (DateFilterCriteria) entity;
        assertEquals(dto.getCondition(), dateCriteria.getCondition());
        assertEquals(dto.getValue(), dateCriteria.getDate());
        assertEquals(filter, dateCriteria.getFilter());
    }

    @Test
    void shouldThrowOnUnknownDtoTonEntity() {
        Filter filter = Filter.builder().id(400L).name("Test").build();

        FilterCriteriaDTO unknownDTO = new FilterCriteriaDTO() {
            @Override
            public FilterType getFilterType() {
                return null;
            }
        };

        assertThrows(IllegalArgumentException.class,
                () -> mapper.toEntity(unknownDTO, filter));
    }
}
