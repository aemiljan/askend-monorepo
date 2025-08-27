package com.github.aemiljan.askend_filters.mapper;

import com.github.aemiljan.askend_filters.controller.dto.AmountFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.FilterDTO;
import com.github.aemiljan.askend_filters.model.entity.AmountFilterCriteria;
import com.github.aemiljan.askend_filters.model.entity.Filter;
import com.github.aemiljan.askend_filters.model.enums.AmountCondition;
import com.github.aemiljan.askend_filters.model.enums.FilterType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilterMapperTest {

    @Autowired
    private FilterMapper filterMapper;

    @Test
    void toDtoShouldMapFilterWithAmountCriteria() {
        AmountFilterCriteria amountCriteria = AmountFilterCriteria.builder()
                .id(1L)
                .type(FilterType.AMOUNT)
                .condition(AmountCondition.GREATER_OR_EQUAL)
                .value(1000L)
                .build();

        Filter filter = Filter.builder()
                .id(10L)
                .name("Test Filter")
                .criteria(List.of(amountCriteria))
                .build();

        FilterDTO dto = filterMapper.toDTO(filter);

        assertNotNull(dto);
        assertEquals(filter.getId(), dto.getId());
        assertEquals(filter.getName(), dto.getName());
        assertEquals(1, dto.getCriteria().size());
        assertTrue(dto.getCriteria().get(0) instanceof AmountFilterCriteriaDTO);
        AmountFilterCriteriaDTO criteriaDTO = (AmountFilterCriteriaDTO) dto.getCriteria().get(0);
        assertEquals(amountCriteria.getCondition(), criteriaDTO.getCondition());
        assertEquals(amountCriteria.getValue(), criteriaDTO.getValue());
    }

    @Test
    void shouldReturnNullWhenFilterIsNull() {
        assertNull(filterMapper.toDTO(null));
    }
}
