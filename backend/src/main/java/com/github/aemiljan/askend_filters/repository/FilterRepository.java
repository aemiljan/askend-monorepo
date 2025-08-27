package com.github.aemiljan.askend_filters.repository;

import com.github.aemiljan.askend_filters.model.entity.Filter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilterRepository extends JpaRepository<Filter, Long> {
}
