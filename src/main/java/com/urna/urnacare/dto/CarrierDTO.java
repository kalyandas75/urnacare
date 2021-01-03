package com.urna.urnacare.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CarrierDTO {
    private Long id;
    @NotBlank
    @Size(max = 255)
    private String name;
}
