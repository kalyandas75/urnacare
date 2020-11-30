package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PaymentRequestItemDTO {
    private String description;
    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal gst;
    private BigDecimal netAmount;
}
