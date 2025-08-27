package com.github.aemiljan.askend_filters.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "filters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Filter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "filter", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FilterCriteria> criteria = new ArrayList<>();

    public void addCriteria(List<FilterCriteria> criteriaList) {
        for (FilterCriteria fc : criteriaList) {
            fc.setFilter(this);
        }
        this.criteria.addAll(criteriaList);
    }
}
