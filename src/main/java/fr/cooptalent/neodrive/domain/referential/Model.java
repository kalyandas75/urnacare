package fr.cooptalent.neodrive.domain.referential;

import fr.cooptalent.neodrive.domain.Vehicle;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Year;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nd_model")
@Data
public class Model implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "label")
    private String label;

    @Column(name = "year")
    private Year year;

    @Column(name = "energy")
    private String energy;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "number_of_doors")
    private Integer numberOfDoors;

    @Column(name = "gear_box_type")
    private String gearBoxType;

    @ManyToOne
    private Brand brand;

    @ManyToMany
    private List<Equipment> equipments;

    @NotNull
    @ManyToMany
    private List<Type> types;

    @OneToMany(mappedBy = "model")
    private List<Vehicle> vehicles;

}
