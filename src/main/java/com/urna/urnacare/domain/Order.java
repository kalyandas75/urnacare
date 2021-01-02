package com.urna.urnacare.domain;

import com.urna.urnacare.enumeration.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "uc_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id ;
    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private User patient;
    private Long consultationId;
    @Embedded
    private OrderAddress shippingAddress;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="orderId", referencedColumnName="id")
    private List<OrderItem> items;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="carrier_id")
    private Carrier carrier;
    private BigDecimal deliveryCharge;
    private String shippingReferenceNumber; // courier reference
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="orderId", referencedColumnName="id")
    private List<OrderStatusHistory> statusHistory;
    private OrderStatus status; // this is the latest status of the order

}
