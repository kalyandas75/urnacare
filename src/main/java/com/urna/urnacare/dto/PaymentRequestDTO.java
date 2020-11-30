package com.urna.urnacare.dto;

import com.urna.urnacare.domain.OrderAddress;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class PaymentRequestDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String productInfo;
    private String amount;
    private String txnId;
    private String hash;
    private String sUrl;
    private String fUrl;
    private String key;
    private String payurl;
    private String udf1;
    private String serviceProvider = "payu_paisa";
    private OrderAddress shippingAddress;
    private List<PaymentRequestItemDTO> items;
}
