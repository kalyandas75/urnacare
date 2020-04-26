package fr.cooptalent.neodrive.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "nd_bankCard")
@Data
public class BankCard implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "number")
    private Long number;

    @Column(name = "validity_date")
    private Date validityDate;

    @Column(name = "cryptogram")
    private Integer cryptogram;

    @ManyToOne
    private User owner;

}
