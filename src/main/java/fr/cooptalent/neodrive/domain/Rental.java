package fr.cooptalent.neodrive.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nd_rental")
@Data
public class Rental implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "distance")
    private Integer distance;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "status")
    private String status;

    @Column(name = "score")
    private Integer score;

    @ManyToMany
    private List<User> tenants;

    @ManyToOne
    private Announcement announcement;

    @NotNull
    @OneToOne
    private Payment payment;

}
