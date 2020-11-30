package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PrescriptionDrugQuantityDTO {
    private Long drugId;
    private Integer quantity;
}
