package com.urna.urnacare.dto;

import com.urna.urnacare.enumeration.DoseUnit;
import com.urna.urnacare.enumeration.DurationUnit;
import com.urna.urnacare.enumeration.Formulation;
import com.urna.urnacare.enumeration.Frequency;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PrescriptionDrugDTO implements Serializable {
    private Long id;
    @NotNull
    private Long drugId;
    private String drugBrand;
    private String drugStrength;
    private String drugCompositionName;
    private Formulation drugFormulation;
    @NotNull
    private BigDecimal dose;
    @NotNull
    private DoseUnit unit;
    @NotNull
    private Frequency frequency;
    @NotNull
    private BigDecimal duration;
    @NotNull
    private DurationUnit durationUnit;
}
