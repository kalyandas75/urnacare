package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.Formulation;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Drug extends AbstractAuditingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Column(nullable = false)
    private String brand;
    @Column(nullable = false)
    private String strength;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="composition_id")
    private Composition composition;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Formulation formulation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @Column
    private Boolean prescriptionRequired = true;
}
