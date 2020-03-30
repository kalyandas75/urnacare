package fr.cooptalent.neodrive.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO extends AbstractAuditingDTO {

    private Long id;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Size(max = 50)
    private String title;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private Boolean newsletterSubscription = false;

    @Size(max = 15)
    private String phoneNumber;

    private LocalDate birthDate;

    @Size(max = 100)
    private String birthPlace;

    @Size(max = 50)
    private String nationalityCode;

    @Size(max = 100)
    private String nationalityName;

    @Size(max = 50)
    private String permitNumber;

    @Size(max = 50)
    private String permitCountryCode;

    @Size(max = 100)
    private String permitCountryName;

    private LocalDate permitDate;

    @Size(max = 50)
    private String iban;

    @Size(max = 50)
    private String bic;

    private boolean activated = false;

    @Size(min = 2, max = 6)
    private String langKey;

    private Set<String> authorities;

    public UserDTO() {
        // Empty constructor needed for Jackson.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Boolean isNewsletterSubscription() {
        return newsletterSubscription;
    }

    public void setNewsletterSubscription(Boolean newsletterSubscription) {
        this.newsletterSubscription = newsletterSubscription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getNationalityCode() {
        return nationalityCode;
    }

    public void setNationalityCode(String nationalityCode) {
        this.nationalityCode = nationalityCode;
    }

    public String getNationalityName() {
        return nationalityName;
    }

    public void setNationalityName(String nationalityName) {
        this.nationalityName = nationalityName;
    }

    public String getPermitNumber() {
        return permitNumber;
    }

    public void setPermitNumber(String permitNumber) {
        this.permitNumber = permitNumber;
    }

    public LocalDate getPermitDate() {
        return permitDate;
    }

    public void setPermitDate(LocalDate permitDate) {
        this.permitDate = permitDate;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public String getPermitCountryCode() {
        return permitCountryCode;
    }

    public void setPermitCountryCode(String permitCountryCode) {
        this.permitCountryCode = permitCountryCode;
    }

    public String getPermitCountryName() {
        return permitCountryName;
    }

    public void setPermitCountryName(String permitCountryName) {
        this.permitCountryName = permitCountryName;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", title='" + title + '\'' +
                ", email='" + email + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", newsletterSubscription=" + newsletterSubscription +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", nationalityCode='" + nationalityCode + '\'' +
                ", nationalityName='" + nationalityName + '\'' +
                ", permitNumber='" + permitNumber + '\'' +
                ", permitCountryCode='" + permitCountryCode + '\'' +
                ", permitCountryName='" + permitCountryName + '\'' +
                ", permitDate=" + permitDate +
                ", iban='" + iban + '\'' +
                ", bic='" + bic + '\'' +
                ", activated=" + activated +
                ", langKey='" + langKey + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
