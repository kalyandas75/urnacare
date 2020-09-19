package com.urna.urnacare.dto;

import com.urna.urnacare.enumeration.Gender;
import com.urna.urnacare.security.AuthoritiesConstants;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * A DTO representing a user, with his authorities.
 */
@Data
@NoArgsConstructor
public class UserDTO extends AbstractAuditingDTO {

    private Long id;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Email
    @Size(min = 5, max = 254)
    private String email;

    private boolean activated = false;

    @Size(max = 256)
    private String imageUrl;

    @Size(max = 20)
    private String authority = AuthoritiesConstants.PATIENT;

    @Size(max = 15)
    private String phoneNumber;

    @Size(max = 15)
    private String alternatePhoneNumber;

    private List<AddressDTO> addresses;

    private Gender gender;
}
