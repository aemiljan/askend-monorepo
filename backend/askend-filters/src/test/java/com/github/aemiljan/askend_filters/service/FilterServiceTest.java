package com.github.aemiljan.askend_filters.service;

import com.github.aemiljan.askend_filters.controller.dto.*;
import com.github.aemiljan.askend_filters.mapper.FilterMapper;
import com.github.aemiljan.askend_filters.model.enums.AmountCondition;
import com.github.aemiljan.askend_filters.model.enums.FilterType;
import com.github.aemiljan.askend_filters.model.enums.TitleCondition;
import com.github.aemiljan.askend_filters.repository.FilterRepository;
import com.github.aemiljan.askend_filters.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FilterServiceTest {

    @Autowired
    private FilterService filterService;

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private FilterMapper filterMapper;

    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    void setUp() {
        filterRepository.deleteAll();
    }

    @Test
    void shouldCreateFilterWithCriteria() {
        FilterDTO saved = testDataUtil.createSampleFilter();

        assertNotNull(saved.getId());
        assertEquals("Sample Filter", saved.getName());
        assertEquals(1, saved.getCriteria().size());

        FilterCriteriaDTO criteria = saved.getCriteria().get(0);
        assertTrue(criteria instanceof AmountFilterCriteriaDTO);
        AmountFilterCriteriaDTO amount = (AmountFilterCriteriaDTO) criteria;
        assertEquals(AmountCondition.GREATER_OR_EQUAL, amount.getCondition());
        assertEquals(1000L, amount.getValue());
    }

    @Test
    void shouldReturnCorrectFilterById() {
        FilterDTO created = testDataUtil.createSampleFilter();

        FilterDTO fetched = filterService.getFilterById(created.getId());

        assertEquals(created.getId(), fetched.getId());
        assertEquals(created.getName(), fetched.getName());
    }

    @Test
    void shouldReplaceNameAndCriteriaOnUpdate() {
        FilterDTO created = testDataUtil.createSampleFilter();

        TitleFilterCriteriaDTO titleDTO = TitleFilterCriteriaDTO.builder()
                .filterType(FilterType.TITLE)
                .condition(TitleCondition.CONTAINS)
                .value("urgent")
                .build();

        FilterCreateRequest updateRequest = FilterCreateRequest.builder()
                .name("Updated Filter")
                .criteria(List.of(titleDTO))
                .build();

        FilterDTO updated = filterService.updateFilter(created.getId(), updateRequest);

        assertEquals("Updated Filter", updated.getName());
        assertEquals(1, updated.getCriteria().size());
        assertTrue(updated.getCriteria().get(0) instanceof TitleFilterCriteriaDTO);
    }

    @Test
    void shouldRemoveFilterAndCriteriaOnDelete() {
        FilterDTO created = testDataUtil.createSampleFilter();

        filterService.deleteFilter(created.getId());

        assertFalse(filterRepository.findById(created.getId()).isPresent());
    }
}
