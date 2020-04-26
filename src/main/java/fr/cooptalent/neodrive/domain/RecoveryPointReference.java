package fr.cooptalent.neodrive.domain;

import fr.cooptalent.neodrive.domain.referential.City;
import fr.cooptalent.neodrive.domain.referential.Country;
import fr.cooptalent.neodrive.domain.referential.Street;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "nd_recoveryPointReference")
@Data
public class RecoveryPointReference implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "label")
    private String label;

    @Column(name = "number")
    private Long number;

    @Column(name = "x_coordinate")
    private Integer xCoordinate;

    @Column(name = "y_coordinate")
    private Integer yCoordinate;

    @ManyToOne
    private AnnouncementAddress announcementAddress;

    @ManyToOne
    private Country country;

    @ManyToOne
    private City city;

    @ManyToOne
    private Street street;

}
