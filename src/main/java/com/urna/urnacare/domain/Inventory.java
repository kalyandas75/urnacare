package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.MeasurementUnit;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Inventory extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="drug_id")
    private Drug drug;
    @Column
    private Integer packSize;
    @Column
    private MeasurementUnit unit;
    @Column
    private Integer noOfUnits;
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=7, fraction=2)
    @Column(nullable = false)
    private BigDecimal price;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column
    private BigDecimal maxDiscountRate;
    @Column
    private String batchNumber;
    @Column
    private LocalDate expiryDate;
    @Column
    private String hsnCode;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column
    private BigDecimal gst;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column
    private BigDecimal cgst;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column
    private BigDecimal sgst;
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column
    private BigDecimal igst;
}
