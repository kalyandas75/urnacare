package com.urna.urnacare.service;

import com.urna.urnacare.domain.PaymentCallback;
import com.urna.urnacare.domain.PaymentDetail;

public interface PaymentService {
	public PaymentDetail proceedPayment(PaymentDetail paymentDetail);
	public String payuCallback(PaymentCallback paymentResponse);
}
