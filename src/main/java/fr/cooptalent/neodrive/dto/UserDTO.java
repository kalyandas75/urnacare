package fr.cooptalent.neodrive.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
public class UserDTO extends AbstractAuditingDTO {

    private UUID id;

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

    private UUID nationalityCode;

    @Size(max = 100)
    private String nationalityName;

    @Size(max = 50)
    private String permitNumber;

    private UUID permitCountryCode;

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
}
