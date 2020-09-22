package com.urna.urnacare.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Manufacturer extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
}
