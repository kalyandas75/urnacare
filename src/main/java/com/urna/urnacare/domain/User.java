package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.Gender;
import com.urna.urnacare.security.AuthoritiesConstants;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.Set;

/**
 * A user.
 */

@Entity
@Table(name = "uc_user")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class User extends AbstractAuditingEntity  {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Column(name = "first_name", length = 100, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;


    @Column(nullable = false)
    private boolean activated = false;

    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Column(name = "activation_key", length = 20)
    private String activationKey;

    @Column(name = "reset_key", length = 20)
    private String resetKey;

    @Column(name = "reset_date", columnDefinition = "TIMESTAMP")
    private Instant resetDate = null;

    @Column(nullable = false, length = 20)
    private String authority = AuthoritiesConstants.PATIENT;

    @Column(length = 15)
    private String phoneNumber;

    @Column(length = 15)
    private String alternatePhoneNumber;

    @OneToMany(mappedBy="user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Gender gender;
}
