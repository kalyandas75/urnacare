package fr.cooptalent.neodrive.dto;

import fr.cooptalent.neodrive.config.Constants;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.io.Serializable;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean isNewsletterSubscription() {
        return newsletterSubscription;
    }

    public void setNewsletterSubscription(Boolean newsletterSubscription) {
        this.newsletterSubscription = newsletterSubscription;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRegistrationDTO{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", newsletterSubscription=" + newsletterSubscription +
                ", langKey='" + langKey + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
