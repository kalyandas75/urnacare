package fr.cooptalent.neodrive.domain.referential;

import fr.cooptalent.neodrive.domain.RecoveryPointReference;
import fr.cooptalent.neodrive.domain.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nd_country")
@Data
public class Country implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID code;

    @Column(name = "label")
    private String name;

    @NotNull
    @OneToMany(mappedBy = "country")
    private List<City> cities;

    @OneToMany(mappedBy = "country")
    private List<RecoveryPointReference> recoveryPointReferences;

}
