package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class DrugDTO implements Serializable {
    private Long id;
    @NotBlank
    private String name;
    @NotNull
    private Set<DrugIngredientDTO> drugIngredients;
}
