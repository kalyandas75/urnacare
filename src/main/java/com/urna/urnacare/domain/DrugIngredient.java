package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.MeasurementUnit;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class DrugIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;
    @Column
    private BigDecimal quantity;
    @Column
    @Enumerated(EnumType.STRING)
    private MeasurementUnit unit;
}
