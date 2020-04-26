package fr.cooptalent.neodrive.dto;

import fr.cooptalent.neodrive.config.Constants;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
public class UserRegistrationDTO implements Serializable {
    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    private Boolean newsletterSubscription = false;

    @Size(min = 2, max = 6)
    private String langKey = Constants.DEFAULT_LANGUAGE;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

}
