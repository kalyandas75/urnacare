package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    private Long patientId;
    private Long consultationId;
    @Embedded
    private Address shippingAddress;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="orderId", referencedColumnName="id")
    private List<OrderItem> items;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="carrier_id")
    private Carrier carrier;
    private BigDecimal deliveryCharge;
    private String shippingReferenceNumber; // courier reference
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="orderId", referencedColumnName="id")
    private List<OrderStatusHistory> statusHistory;

}
