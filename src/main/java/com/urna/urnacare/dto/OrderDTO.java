package com.urna.urnacare.dto;

import com.urna.urnacare.domain.*;
import com.urna.urnacare.enumeration.OrderStatus;
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
    private String patientFirstName;
    private String patientLastName;
    private String patientEmail;
    private String patientPhoneNumber;
    private Long consultationId;
    private OrderAddress shippingAddress;
    private List<OrderItemDTO> items;
    private Long carrierId;
    private Long carrierName;
    private BigDecimal deliveryCharge;
    private String shippingReferenceNumber; // courier reference
    private List<OrderStatusHistory> statusHistory;
    private OrderStatus status;
}
