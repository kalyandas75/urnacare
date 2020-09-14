package com.urna.urnacare.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
}
