package com.urna.urnacare.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Embeddable
@Data
@NoArgsConstructor
public class OrderAddress {

    @Pattern(regexp = "[1-9][0-9]{9}")
    private String phoneNumber;
    @Size(max = 255)
    @NotBlank
    private String address;
    @NotBlank
    @Size(max = 255)
    private String city;
    @NotBlank
    @Size(max = 255)
    private String state;
    @Pattern(regexp = "[1-9][0-9]{5}")
    @NotBlank
    private String pin;
}
