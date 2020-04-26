package fr.cooptalent.neodrive.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static fr.cooptalent.neodrive.dto.UserRegistrationDTO.PASSWORD_MAX_LENGTH;
import static fr.cooptalent.neodrive.dto.UserRegistrationDTO.PASSWORD_MIN_LENGTH;

/**
 * View Model object for storing a user's credentials.
 */
@Data
public class LoginDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private Boolean rememberMe;

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
}
