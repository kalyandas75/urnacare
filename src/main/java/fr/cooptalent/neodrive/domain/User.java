package fr.cooptalent.neodrive.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.cooptalent.neodrive.domain.referential.Country;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

/**
 * A user.
 */

@Entity
@Table(name = "nd_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Data
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password_hash", length = 60, nullable = false)
    private String password;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 50)
    @Column(name = "title", length = 50)
    private String title;

    @Email
    @Size(min = 5, max = 254)
    @Column(length = 254, unique = true)
    private String email;

    @Column(nullable = true)
    private Boolean newsletterSubscription = false;

    @Column(length = 15)
    private String phoneNumber;

    @Column(columnDefinition = "DATE")
    private LocalDate birthDate;

    @Column(length = 100)
    private String birthPlace;

    @ManyToOne
    @JoinColumn(name = "nationality_code")
    private Country nationality;

    @Column(length = 50)
    private String permitNumber;

    @ManyToOne
    @JoinColumn(name = "permit_country_code")
    private Country permitCountry;

    @Column(columnDefinition = "DATE")
    private LocalDate permitDate;

    @Column(length = 50)
    private String iban;

    @Column(length = 50)
    private String bic;

    @NotNull
    @Column(nullable = false)
    private boolean activated = false;

    @Size(min = 2, max = 6)
    @Column(name = "lang_key", length = 6)
    private String langKey;

    @Size(max = 256)
    @Column(name = "image_url", length = 256)
    private String imageUrl;

    @Size(max = 20)
    @Column(name = "activation_key", length = 20)
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Column(name = "reset_key", length = 20)
    @JsonIgnore
    private String resetKey;

    @Column(name = "reset_date", columnDefinition = "TIMESTAMP")
    private Instant resetDate = null;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "nd_user_authority",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "name")})
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @OneToMany(mappedBy = "transmitter")
    private List<Message> messagesSent;

    @OneToMany(mappedBy = "receiver")
    private List<Message> messagesReceived;

    @OneToMany(mappedBy = "owner")
    private List<BankCard> bankCards;

    @OneToMany(mappedBy = "user")
    private List<Photo> photos;

    @OneToMany(mappedBy = "user")
    private List<Vehicle> vehicles;

    @OneToMany(mappedBy = "user")
    private List<Announcement> announcements;

    @ManyToMany(mappedBy = "tenants")
    private List<Rental> rentals;

    @OneToMany(mappedBy = "tenant")
    private List<Payment> tenantPayments;

    @OneToMany(mappedBy = "owner")
    private List<Payment> ownerPayments;

}
