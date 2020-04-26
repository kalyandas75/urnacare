package fr.cooptalent.neodrive.domain.referential;

import fr.cooptalent.neodrive.domain.Vehicle;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nd_type")
@Data
public class Type implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "label")
    private String label;

    @Column(name = "price_per_km")
    private Integer pricePerKm;

    @NotNull
    @ManyToMany(mappedBy = "types")
    private List<Model> models;

    @OneToMany
    private List<Vehicle> vehicles;

}
