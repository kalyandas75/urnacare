package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.PaymentMode;
import com.urna.urnacare.enumeration.PaymentStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Data
public class AppointmentPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String transactionId;

    @Column(nullable = false)
    private Long appointmentRequestId;
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
    @Column(columnDefinition="TEXT")
    private String paymentResponse;
    @Column(precision = 7, scale = 2)
    private BigDecimal amount;
}
