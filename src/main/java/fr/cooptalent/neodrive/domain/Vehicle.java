package fr.cooptalent.neodrive.domain;

import fr.cooptalent.neodrive.domain.referential.Brand;
import fr.cooptalent.neodrive.domain.referential.Equipment;
import fr.cooptalent.neodrive.domain.referential.Model;
import fr.cooptalent.neodrive.domain.referential.Type;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nd_vehicle")
@Data
public class Vehicle implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "vehicle_description")
    private String vehicleDescription;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "vehicle")
    private List<Photo> photos;

    @NotNull
    @OneToMany(mappedBy = "vehicle")
    private List<Insurance> insurances;

    @ManyToMany(mappedBy = "vehicles")
    private List<OptionReference> optionReferences;

    @ManyToMany(mappedBy = "vehicles")
    private List<FurtherInformation> furtherInformations;

    @OneToMany(mappedBy = "vehicle")
    private List<Announcement> announcements;

    @ManyToOne
    private Brand brand;

    @ManyToOne
    private Model model;

    @ManyToMany
    private List<Equipment> equipments;

    @ManyToOne
    private Type type;

}
