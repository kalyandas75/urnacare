package com.urna.urnacare.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Composition extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
}
