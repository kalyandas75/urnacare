package com.urna.urnacare.dto;

import com.urna.urnacare.enumeration.Formulation;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OrderItemDTO implements Serializable {
    private Long id;
    @NonNull
    private Long drugId;
    private String drugBrand;
    private String drugStrength;
    private String drugCompositionName;
    private Formulation drugFormulation;
    private String drugManufacturerName;
    private Boolean drugPrescriptionRequired;

    @Min(1)
    @NotNull
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal cgst;
    private BigDecimal sgst;
    private BigDecimal igst;
    private BigDecimal discountRate;
}
