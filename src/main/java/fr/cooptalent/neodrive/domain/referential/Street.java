package fr.cooptalent.neodrive.domain.referential;

import fr.cooptalent.neodrive.domain.RecoveryPointReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nd_street")
@Data
public class Street implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "label")
    private String label;

    @ManyToOne
    private City city;

    @OneToMany(mappedBy = "street")
    private List<Station> stations;

    @OneToMany(mappedBy = "street")
    private List<RecoveryPointReference> recoveryPointReferences;

}
