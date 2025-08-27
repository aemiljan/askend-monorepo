package com.github.aemiljan.askend_filters.controller;

import com.github.aemiljan.askend_filters.controller.dto.FilterCreateRequest;
import com.github.aemiljan.askend_filters.controller.dto.FilterDTO;
import com.github.aemiljan.askend_filters.model.entity.Filter;
import com.github.aemiljan.askend_filters.service.FilterService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filters")
@RequiredArgsConstructor
public class FilterController {

    private final FilterService filterService;


    @Operation(summary = "Create a new filter")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<FilterDTO> postNewsArticle(@Valid @RequestBody FilterCreateRequest request) {
        return new ResponseEntity<>(filterService.createFilter(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Get all filters")
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<FilterDTO> getAllNewsArticles() {
        return filterService.getAllFilters();
    }

    @Operation(summary = "Get a filter by ID")
    @GetMapping(value = "/{id}")
    public ResponseEntity<FilterDTO> getNewsArticleById(@PathVariable("id") long filterId){
        return new ResponseEntity<>(filterService.getFilterById(filterId), HttpStatus.OK);
    }

    @Operation(summary = "Delete a filter by ID")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable("id") long filterId) {
        filterService.deleteFilter(filterId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update an existing filter")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FilterDTO> updateFilter(
            @PathVariable("id") long filterId,
            @Valid @RequestBody FilterCreateRequest request) {
        return ResponseEntity.ok(filterService.updateFilter(filterId, request));
    }
}
