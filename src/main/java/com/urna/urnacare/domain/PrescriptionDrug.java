package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.DoseUnit;
import com.urna.urnacare.enumeration.DurationUnit;
import com.urna.urnacare.enumeration.Frequency;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class PrescriptionDrug {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name="drug_id")
    private Drug drug;
    @Column
    private BigDecimal dose;
    @Enumerated(EnumType.STRING)
    @Column
    private DoseUnit unit;
    @Enumerated(EnumType.STRING)
    @Column
    private Frequency frequency;
    @Column
    private BigDecimal duration;
    @Enumerated(EnumType.STRING)
    @Column
    private DurationUnit durationUnit;
}
