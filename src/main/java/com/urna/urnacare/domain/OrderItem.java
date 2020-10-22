package com.urna.urnacare.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@Entity
@Data
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="drug_id")
    private Drug drug;
    @Column
    private Integer quantity;
    @Column
    private BigDecimal pricePerUnit;
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
    @Digits(integer=3, fraction=2)
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Column
    private BigDecimal discountRate;
}
