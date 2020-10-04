package com.urna.urnacare.dto;

import com.urna.urnacare.enumeration.Formulation;
import com.urna.urnacare.enumeration.MeasurementUnit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class InventoryDTO extends AbstractAuditingDTO {
    private Long id;
    @NotNull
    private Long drugId;

    private String brand;
    private Long compositionId;
    private String compositionName;
    private Formulation formulation;
    private Long manufacturerId;
    private String manufacturerName;
    private Boolean prescriptionRequired;
    private String strength;
    @NotNull
    @Min(value = 0)
    private Integer packSize;
    @NotNull
    private MeasurementUnit unit;
    @NotNull
    @Min(value = 1)
    private Integer noOfUnits;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=7, fraction=2)
    @NotNull
    private BigDecimal price;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal maxDiscountRate;
    @NotBlank
    private String batchNumber;
    @NotNull
    @FutureOrPresent
    private LocalDate expiryDate;

    private String hsnCode;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal gst;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal cgst;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal sgst;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    private BigDecimal igst;
}
