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
    @NotBlank
    @Size(max = 255)
    private String brand;
    @NotNull
    private Long compositionId;
    private String compositionName;
    @NotNull
    private Formulation formulation;
    @NotNull
    private Long manufacturerId;
    private String manufacturerName;
    @NotNull
    @Min(value = 0)
    private Integer packSize;
    @NotNull
    private MeasurementUnit unit;
    @NotNull
    @Min(value = 1)
    private Integer noOfUnits;
    private Boolean prescriptionRequired = true;
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
