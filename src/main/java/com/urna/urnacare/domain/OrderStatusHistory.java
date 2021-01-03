package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Data
@Entity
public class OrderStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(columnDefinition = "TIMESTAMP")
    private Instant statusDate = Instant.now();
    private String comments;
}
