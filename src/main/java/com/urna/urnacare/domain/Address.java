package com.urna.urnacare.domain;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@Data
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "line_1", length = 100, nullable = false)
    private String line1;

    @Column(name = "line_2", length = 100)
    private String line2;

    @Column(name = "line_3", length = 100)
    private String line3;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "state", length = 100, nullable = false)
    private String state;

    @Column(name = "pin", length = 10)
    private String pin;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "current")
    private Boolean current;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;

}
