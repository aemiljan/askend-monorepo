package com.github.aemiljan.askend_filters.model.entity;

import com.github.aemiljan.askend_filters.model.enums.AmountCondition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "amount_filter_criteria")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class AmountFilterCriteria extends FilterCriteria {
    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type")
    private AmountCondition condition;

    @Column(name = "amount_value")
    private Long value;
}
