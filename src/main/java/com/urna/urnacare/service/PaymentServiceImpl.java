package com.urna.urnacare.service;


import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.urna.urnacare.domain.Payment;
import com.urna.urnacare.domain.PaymentCallback;
import com.urna.urnacare.domain.PaymentDetail;
import com.urna.urnacare.enumeration.PaymentStatus;
import com.urna.urnacare.repository.PaymentRepo;
import com.urna.urnacare.util.PaymentUtil;

public class PaymentServiceImpl implements PaymentService {
	 @Autowired
	 PaymentRepo paymentRepository;

	    @Override
	    public PaymentDetail proceedPayment(PaymentDetail paymentDetail) {
	        PaymentUtil paymentUtil = new PaymentUtil();
	        paymentDetail = paymentUtil.populatePaymentDetail(paymentDetail);
	        savePaymentDetail(paymentDetail);
	        return paymentDetail;
	    }

	    @Override
	    public String payuCallback(PaymentCallback paymentResponse) {
	        String msg = "Transaction failed.";
	        Payment payment = paymentRepository.findByTxnId(paymentResponse.getTxnid());
	        if(payment != null) {
	            //TODO validate the hash
	            PaymentStatus paymentStatus = null;
	            if(paymentResponse.getStatus().equals("failure")){
	                paymentStatus = PaymentStatus.Failed;
	            }else if(paymentResponse.getStatus().equals("success")) {
	                paymentStatus = PaymentStatus.Success;
	                msg = "Transaction success";
	            }
	            payment.setStatus(paymentStatus);
	            payment.setMihpayid(paymentResponse.getMihpayid());
	            payment.setMode(paymentResponse.getMode());
	            paymentRepository.save(payment);
	        }
	        return msg;
	    }

	    private void savePaymentDetail(PaymentDetail paymentDetail) {
	        Payment payment = new Payment();
	        payment.setAmount(Double.parseDouble(paymentDetail.getAmount()));
	        payment.setEmail(paymentDetail.getEmail());
	        payment.setName(paymentDetail.getName());
	        java.util.Date dt = new java.util.Date();
	        payment.setPaymentDate(new Date(dt.getTime()));
	        payment.setStatus(PaymentStatus.Pending);
	        payment.setPhone(paymentDetail.getPhone());
	        payment.setProductInfo(paymentDetail.getProductInfo());
	        payment.setTransactionId(paymentDetail.getTxnId());
	        paymentRepository.save(payment);
	    }

}
