package com.github.aemiljan.askend_filters.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aemiljan.askend_filters.controller.dto.AmountFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.controller.dto.FilterCreateRequest;
import com.github.aemiljan.askend_filters.controller.dto.FilterDTO;
import com.github.aemiljan.askend_filters.controller.dto.TitleFilterCriteriaDTO;
import com.github.aemiljan.askend_filters.model.enums.AmountCondition;
import com.github.aemiljan.askend_filters.model.enums.FilterType;
import com.github.aemiljan.askend_filters.model.enums.TitleCondition;
import com.github.aemiljan.askend_filters.repository.FilterRepository;
import com.github.aemiljan.askend_filters.service.FilterService;
import com.github.aemiljan.askend_filters.util.TestDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FilterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FilterService filterService;

    @Autowired
    private FilterRepository filterRepository;

    @Autowired
    private TestDataUtil testDataUtil;

    @BeforeEach
    void setUp() {
        filterRepository.deleteAll();
    }

    @Test
    void shouldReturnCreatedFilter() throws Exception {
        AmountFilterCriteriaDTO amountDTO = AmountFilterCriteriaDTO.builder()
                .filterType(FilterType.AMOUNT)
                .condition(AmountCondition.GREATER_OR_EQUAL)
                .value(1000L)
                .build();

        FilterCreateRequest request = FilterCreateRequest.builder()
                .name("High Value Orders")
                .criteria(List.of(amountDTO))
                .build();

        mockMvc.perform(post("/api/filters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("High Value Orders"))
                .andExpect(jsonPath("$.criteria[0].filterType").value("AMOUNT"))
                .andExpect(jsonPath("$.criteria[0].condition").value("GREATER_OR_EQUAL"))
                .andExpect(jsonPath("$.criteria[0].value").value(1000));
    }

    @Test
    void shouldReturnFiltersList() throws Exception {
        testDataUtil.createSampleFilter();

        mockMvc.perform(get("/api/filters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Sample Filter"));
    }

    @Test
    void shouldReturnFilterById() throws Exception {
        FilterDTO created = testDataUtil.createSampleFilter();

        mockMvc.perform(get("/api/filters/{id}", created.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()))
                .andExpect(jsonPath("$.name").value("Sample Filter"));
    }

    @Test
    void shouldModifyFilterOnUpdate() throws Exception {
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

        mockMvc.perform(put("/api/filters/{id}", created.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Filter"))
                .andExpect(jsonPath("$.criteria[0].filterType").value("TITLE"));
    }

    @Test
    void shouldRemoveFilterOnDelete() throws Exception {
        FilterDTO created = testDataUtil.createSampleFilter();

        mockMvc.perform(delete("/api/filters/{id}", created.getId()))
                .andExpect(status().isNoContent());

        assertFalse(filterRepository.findById(created.getId()).isPresent());
    }
}
