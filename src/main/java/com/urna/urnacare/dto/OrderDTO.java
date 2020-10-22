package com.urna.urnacare.dto;

import com.urna.urnacare.domain.Address;
import com.urna.urnacare.domain.Carrier;
import com.urna.urnacare.domain.OrderItem;
import com.urna.urnacare.domain.OrderStatusHistory;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDTO implements Serializable {
    private Long id ;
    private Long patientId;
    private Long consultationId;
    private Address shippingAddress;
    private List<OrderItemDTO> items;
    private Long carrierId;
    private Long carrierName;
    private BigDecimal deliveryCharge;
    private String shippingReferenceNumber; // courier reference
    private List<OrderStatusHistory> statusHistory;
}
