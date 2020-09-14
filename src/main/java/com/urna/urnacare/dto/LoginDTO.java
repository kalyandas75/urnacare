package com.urna.urnacare.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
@Data
public class LoginDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @NotNull
    @Size(min = DoctorRegistrationDTO.PASSWORD_MIN_LENGTH, max = DoctorRegistrationDTO.PASSWORD_MAX_LENGTH)
    private String password;

    private Boolean rememberMe;

}
