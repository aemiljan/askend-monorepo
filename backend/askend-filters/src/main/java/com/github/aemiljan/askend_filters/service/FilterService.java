package com.github.aemiljan.askend_filters.service;

import com.github.aemiljan.askend_filters.controller.dto.FilterCreateRequest;
import com.github.aemiljan.askend_filters.controller.dto.FilterDTO;
import com.github.aemiljan.askend_filters.exception.ResourceNotFoundException;
import com.github.aemiljan.askend_filters.mapper.FilterCriteriaMapper;
import com.github.aemiljan.askend_filters.mapper.FilterMapper;
import com.github.aemiljan.askend_filters.model.entity.Filter;
import com.github.aemiljan.askend_filters.model.entity.FilterCriteria;
import com.github.aemiljan.askend_filters.repository.FilterRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {

    private final FilterRepository filterRepository;
    private final FilterMapper filterMapper;
    private final FilterCriteriaMapper filterCriteriaMapper;

    public FilterService(FilterRepository filterRepository, FilterMapper filterMapper, FilterCriteriaMapper filterCriteriaMapper) {
        this.filterRepository = filterRepository;
        this.filterMapper = filterMapper;
        this.filterCriteriaMapper = filterCriteriaMapper;
    }

    public FilterDTO createFilter(FilterCreateRequest request) {
        return filterMapper.toDTO(createFilterEntity(request));
    }

    public List<FilterDTO> getAllFilters() {
        return getAllFilterEntities().stream()
                .map(filterMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FilterDTO getFilterById(long filterId) {
        return filterMapper.toDTO(getFilterEntityById(filterId));
    }

    @Transactional
    public void deleteFilter(long filterId) {
        Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new ResourceNotFoundException("Filter", "id", filterId));
        filterRepository.delete(filter);
    }

    public FilterDTO updateFilter(long filterId, FilterCreateRequest request) {
        return filterMapper.toDTO(updateFilterEntity(filterId, request));
    }

    private Filter createFilterEntity(FilterCreateRequest request) {
        Filter filter = Filter.builder()
                .name(request.getName())
                .createdAt(LocalDateTime.now())
                .build();

        List<FilterCriteria> criteriaList = request.getCriteria().stream()
                .map(dto -> filterCriteriaMapper.toEntity(dto, filter))
                .toList();

        filter.addCriteria(criteriaList);

        return filterRepository.save(filter);
    }

    private List<Filter> getAllFilterEntities() {
        return filterRepository.findAll();
    }

    private Filter getFilterEntityById(long filterId) {
        return filterRepository.findById(filterId)
                .orElseThrow(() -> new ResourceNotFoundException("Filter", "id", filterId));
    }

    private Filter updateFilterEntity(long filterId, FilterCreateRequest request) {
        Filter filter = filterRepository.findById(filterId)
                .orElseThrow(() -> new ResourceNotFoundException("Filter", "id", filterId));

        Filter newFilter = Filter.builder()
                .id(filter.getId())
                .name(request.getName())
                .createdAt(filter.getCreatedAt())
                .build();

        List<FilterCriteria> criteriaList = request.getCriteria().stream()
                .map(dto -> filterCriteriaMapper.toEntity(dto, newFilter))
                .toList();

        newFilter.addCriteria(criteriaList);

        return filterRepository.save(newFilter);
    }
}
