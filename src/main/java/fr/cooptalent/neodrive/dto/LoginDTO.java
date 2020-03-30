package fr.cooptalent.neodrive.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static fr.cooptalent.neodrive.dto.UserRegistrationDTO.PASSWORD_MAX_LENGTH;
import static fr.cooptalent.neodrive.dto.UserRegistrationDTO.PASSWORD_MIN_LENGTH;

/**
 * View Model object for storing a user's credentials.
 */
public class LoginDTO {

    @NotNull
    @Size(min = 1, max = 50)
    private String email;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private Boolean rememberMe;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rememberMe=" + rememberMe +
                '}';
    }
}
