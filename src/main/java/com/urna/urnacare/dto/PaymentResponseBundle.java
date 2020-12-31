package com.urna.urnacare.dto;

public class PaymentResponseBundle {
    private PayUMoneyPaymentResponseDTO responseDTO;
    private String responseString;

    public PayUMoneyPaymentResponseDTO getResponseDTO() {
        return responseDTO;
    }

    public void setResponseDTO(PayUMoneyPaymentResponseDTO responseDTO) {
        this.responseDTO = responseDTO;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }
}
