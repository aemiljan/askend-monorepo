package com.github.aemiljan.askend_filters.model.entity;

import com.github.aemiljan.askend_filters.model.enums.DateCondition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Table(name = "date_filter_criteria")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class DateFilterCriteria extends FilterCriteria {
    @Enumerated(EnumType.STRING)
    @Column(name = "condition_type")
    private DateCondition condition;


    private LocalDate date;
}
