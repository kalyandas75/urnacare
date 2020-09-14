package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class AddressDTO implements Serializable {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String line1;

    @Size(max = 100)
    private String line2;

    @Size(max = 100)
    private String line3;

    @NotBlank
    @Size(max = 100)
    private String city;

    @NotBlank
    @Size(max = 100)
    private String state;

    @NotBlank
    @Size(max = 10)
    private String pin;

    @NotBlank
    @Size(max = 10)
    private String type;

    private Boolean current;

    private Long userId;
}
