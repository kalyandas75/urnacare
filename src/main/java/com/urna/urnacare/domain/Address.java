package com.urna.urnacare.domain;


import com.urna.urnacare.enumeration.AddressType;
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

    @Column(name = "name")
    private String name;

    @Column(length = 15)
    private String phoneNumber;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @Column(name = "state", length = 100, nullable = false)
    private String state;

    @Column(name = "pin", length = 10)
    private String pin;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "type", nullable = false)
    private AddressType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="user_id")
    private User user;

}
