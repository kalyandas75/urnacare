package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.PaymentMode;
import com.urna.urnacare.enumeration.PaymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String transactionId;

    @Column(nullable = false)
    private Long orderId;
    @Column(columnDefinition = "TIMESTAMP")
    private Instant createdOn;
    @Column(columnDefinition = "TIMESTAMP")
    private Instant responseOn;
    @Enumerated(value = EnumType.STRING)
    @Column
    private PaymentMode mode;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;
    @Column
    private String responseId;
}
