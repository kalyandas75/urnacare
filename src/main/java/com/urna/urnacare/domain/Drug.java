package com.urna.urnacare.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Drug {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "drug")
    private Set<DrugIngredient> drugIngredients;
}
