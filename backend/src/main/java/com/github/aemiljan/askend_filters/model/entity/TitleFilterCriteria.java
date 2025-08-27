package com.github.aemiljan.askend_filters.model.entity;

import com.github.aemiljan.askend_filters.model.enums.TitleCondition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "title_filter_criteria")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class TitleFilterCriteria extends FilterCriteria {
    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type")
    private TitleCondition condition;

    @Column(name = "condition_value")
    private String value;
}
