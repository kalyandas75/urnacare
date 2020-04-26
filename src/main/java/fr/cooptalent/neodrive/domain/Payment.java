package fr.cooptalent.neodrive.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "nd_payment")
@Data
public class Payment implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "status")
    private String status;

    @OneToOne
    private Rental rental;

    @ManyToOne
    private User tenant;

    @ManyToOne
    private User owner;

}
