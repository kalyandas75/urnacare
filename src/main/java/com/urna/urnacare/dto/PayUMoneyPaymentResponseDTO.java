package com.urna.urnacare.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PayUMoneyPaymentResponseDTO {
    private String mihpayid;
    private String mode;
    private String status;
    private String txnid;
    private String amount;
    private String productinfo;
    private String firstname;
    private String email;
    private String phone;
    private String udf1; // this is payment id
    private String hash;
}
