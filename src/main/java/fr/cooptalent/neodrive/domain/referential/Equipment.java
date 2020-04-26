package fr.cooptalent.neodrive.domain.referential;

import fr.cooptalent.neodrive.domain.Vehicle;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nd_equipment")
@Data
public class Equipment implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "label")
    private String label;

    @ManyToMany(mappedBy = "equipments")
    private List<Model> models;

    @ManyToMany(mappedBy = "equipments")
    private List<Vehicle> vehicles;

}
