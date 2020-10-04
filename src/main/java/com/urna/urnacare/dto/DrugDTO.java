package com.urna.urnacare.dto;

import com.urna.urnacare.enumeration.Formulation;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DrugDTO extends AbstractAuditingDTO{
    private Long id ;
    @NotBlank
    private String brand;
    private String strength;
    @NotNull
    private Long compositionId;
    private String compositionName;
    @NotNull
    private Formulation formulation;
    @NotNull
    private Long manufacturerId;
    private String manufacturerName;
    private Boolean prescriptionRequired = true;
}
