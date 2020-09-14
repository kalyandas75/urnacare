package com.urna.urnacare.dto;

import com.urna.urnacare.enumeration.MeasurementUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class DrugIngredientDTO implements Serializable {
    private Long id;
    @NotNull
    private Long drugId;
    private String drugName;
    @NotNull
    private Long ingredientId;
    private String ingredientName;
    @NotNull
    private BigDecimal quantity;
    @NotNull
    private MeasurementUnit unit;
}
