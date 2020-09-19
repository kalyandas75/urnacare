package com.urna.urnacare.dto;

import com.urna.urnacare.enumeration.AddressType;
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
    @Size(max = 255)
    private String address;

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
    private AddressType type;

    private Long userId;
}
