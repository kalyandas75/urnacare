package fr.cooptalent.neodrive.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "nd_announcement")
@Data
public class Announcement implements Serializable {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "min_duration")
    private Integer minDuration;

    @Column(name = "max_duration")
    private Integer maxDuration;

    @Column(name = "min_distance")
    private Integer minDistance;

    @Column(name = "max_distance")
    private Integer maxDistance;

    @Column(name = "daily_price")
    private Integer dailyPrice;

    @Column(name = "announcement_description")
    private String announcementDescription;

    @ManyToOne
    private User user;

    @ManyToOne
    private Vehicle vehicle;

    @NotNull
    @OneToMany(mappedBy = "announcement")
    private List<Availability> availabilities;

    @NotNull
    @OneToMany(mappedBy = "announcement")
    private List<AnnouncementAddress> announcementAddresses;

    @OneToMany(mappedBy = "announcement")
    private List<Rental> rentals;

}
