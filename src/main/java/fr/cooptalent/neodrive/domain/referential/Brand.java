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
@Table(name = "nd_brand")
@Data
public class Brand implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "label")
    private String label;

    @NotNull
    @OneToMany(mappedBy = "brand")
    private List<Model> models;

    @OneToMany(mappedBy = "brand")
    private List<Vehicle> vehicles;

}
