package com.urna.urnacare.service;

import com.urna.urnacare.config.ApplicationProperties;
import com.urna.urnacare.domain.*;
import com.urna.urnacare.dto.PayUMoneyPaymentResponseDTO;
import com.urna.urnacare.dto.PaymentRequestDTO;
import com.urna.urnacare.dto.PaymentRequestItemDTO;
import com.urna.urnacare.enumeration.OrderStatus;
import com.urna.urnacare.enumeration.PaymentMode;
import com.urna.urnacare.enumeration.PaymentStatus;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.repository.OrderRepository;
import com.urna.urnacare.repository.PaymentRepository;
import com.urna.urnacare.repository.UserRepository;
import com.urna.urnacare.util.GenericUtil;
import com.urna.urnacare.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final ApplicationProperties applicationProperties;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public PaymentService(PaymentRepository paymentRepository, ApplicationProperties applicationProperties, OrderRepository orderRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.applicationProperties = applicationProperties;

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    public PaymentRequestDTO paymentInit(Long orderId) {
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        if(!orderOptional.isPresent()) {
            throw new BadRequestAlertException("Order not found", "order", "notFound");
        }
        Order order = orderOptional.get();
        List<OrderItem> items = order.getItems();
        final BigDecimal[] orderAmount = {BigDecimal.ZERO};
        List<PaymentRequestItemDTO> paymentRequestItemDTOS = new ArrayList<>();
        items.stream().forEach(orderItem -> {
            BigDecimal itemAmount = orderItem.getPricePerUnit().multiply(new BigDecimal(orderItem.getQuantity()));
            BigDecimal discount = itemAmount.multiply(orderItem.getDiscountRate().divide(BigDecimal.valueOf(100)));
            BigDecimal itemAmountAfterDiscount = itemAmount.subtract(discount);
            BigDecimal gst = orderItem.getCgst().add(orderItem.getIgst()).add(orderItem.getSgst());
            BigDecimal gstAmount = itemAmountAfterDiscount.multiply(gst).divide(BigDecimal.valueOf(100));
            BigDecimal netItemAmount = itemAmountAfterDiscount.add(gstAmount);
            orderAmount[0] = orderAmount[0].add(netItemAmount);
            PaymentRequestItemDTO paymentRequestItemDTO = new PaymentRequestItemDTO();
            Drug drug = orderItem.getDrug();
            String description = drug.getBrand() + "-" + drug.getStrength() + " (" + drug.getComposition().getName() + ") " + drug.getFormulation() + " x " + orderItem.getQuantity();
            paymentRequestItemDTO.setDescription(description);
            paymentRequestItemDTO.setAmount(itemAmount);
            paymentRequestItemDTO.setDiscount(discount);
            paymentRequestItemDTO.setGst(gstAmount);
            paymentRequestItemDTO.setNetAmount(netItemAmount);
            paymentRequestItemDTOS.add(paymentRequestItemDTO);
        });
        List<Payment> existingPayments = this.paymentRepository.findByOrderId(orderId);
        Payment payment = null;
        if(existingPayments != null && existingPayments.size() > 0) {
            List<PaymentStatus> paymentStatuses = existingPayments.stream().map(p -> p.getStatus()).collect(Collectors.toList());
            if(paymentStatuses.contains(PaymentStatus.Failed) || paymentStatuses.contains(PaymentStatus.Success)) {
                throw new BadRequestAlertException("The Payment for this order is either made or failed. Please try another order.", "payment", "paymentFailedOrSucceeded");
            }
            if(paymentStatuses.contains(PaymentStatus.Pending)) {
                payment = existingPayments.stream().filter(p -> p.getStatus() == PaymentStatus.Pending).collect(Collectors.toList()).get(0);
            }
        }
        if(payment == null) {
            String trxnId = RandomUtil.generateRandomAlphaNumeric(15);
            payment = new Payment();
            payment.setMode(PaymentMode.PAYUM);
            payment.setOrderId(order.getId());
            payment.setStatus(PaymentStatus.Pending);
            payment.setTransactionId(trxnId);
            payment = this.paymentRepository.save(payment);
        }
        String formattedAmount = GenericUtil.formatAmount(orderAmount[0]);
        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO();
        paymentRequestDTO.setAmount(formattedAmount);
        User user = this.userRepository.getOne(order.getPatientId());
        paymentRequestDTO.setEmail(user.getEmail());
        paymentRequestDTO.setItems(paymentRequestItemDTOS);
        paymentRequestDTO.setFirstName(user.getFirstName());
        paymentRequestDTO.setLastName(user.getLastName());
        paymentRequestDTO.setPhone(user.getPhoneNumber());
        paymentRequestDTO.setProductInfo("Medicine purchase against order id:" + order.getId());
        paymentRequestDTO.setKey(applicationProperties.getPayment().getMerchantKey());
        paymentRequestDTO.setSUrl(applicationProperties.getPayment().getCallbackUrl());
        paymentRequestDTO.setFUrl(applicationProperties.getPayment().getCallbackUrl());
        paymentRequestDTO.setPayurl(applicationProperties.getPayment().getPayurl());
        paymentRequestDTO.setTxnId(payment.getTransactionId());
        String unHashed = applicationProperties.getPayment().getMerchantKey()
                + "|" + payment.getTransactionId()
                + "|" + formattedAmount
                + "|" + paymentRequestDTO.getProductInfo()
                + "|" + user.getFirstName()
                + "|" + user.getEmail()
                + "|" + payment.getId()
                + "|||||||||"
                + "|" + applicationProperties.getPayment().getMerchantSalt();
        paymentRequestDTO.setHash(GenericUtil.calculateHash("SHA-512", unHashed));
        paymentRequestDTO.setShippingAddress(order.getShippingAddress());
        paymentRequestDTO.setUdf1(payment.getId() + "");
        return paymentRequestDTO;
    }


    public String handlePaymentResponse(PayUMoneyPaymentResponseDTO paymentResponseDTO, String paymentResponseDump) {
        String transactionId = paymentResponseDTO.getTxnid();
        Payment payment = this.paymentRepository.findOneByTransactionId(transactionId);
        if(payment == null) {
            return "Payment Not Found";
        }
        Order order = this.orderRepository.findById(payment.getOrderId()).get();
        final BigDecimal[] orderAmount = {BigDecimal.ZERO};
        order.getItems().stream().forEach(orderItem -> {
            BigDecimal itemAmount = orderItem.getPricePerUnit().multiply(new BigDecimal(orderItem.getQuantity()));
            BigDecimal discount = itemAmount.multiply(orderItem.getDiscountRate().divide(BigDecimal.valueOf(100)));
            BigDecimal itemAmountAfterDiscount = itemAmount.subtract(discount);
            BigDecimal gst = orderItem.getCgst().add(orderItem.getIgst()).add(orderItem.getSgst());
            BigDecimal gstAmount = itemAmountAfterDiscount.multiply(gst).divide(BigDecimal.valueOf(100));
            BigDecimal netItemAmount = itemAmountAfterDiscount.add(gstAmount);
            orderAmount[0] = orderAmount[0].add(netItemAmount);
        });
        String productInfo = "Medicine purchase against order id:" + order.getId();
        User patient = this.userRepository.findById(order.getPatientId()).get();
        String receivedHash = paymentResponseDTO.getHash();
        String calculatedUnhashed = applicationProperties.getPayment().getMerchantSalt() + "|" +
                paymentResponseDTO.getStatus() + "|" +
                "|||||||||" +
                payment.getId() + "|" +
                patient.getEmail() + "|" +
                patient.getFirstName() + "|" +
                productInfo + "|" +
                GenericUtil.formatAmount(orderAmount[0]) + "|" +
                payment.getTransactionId() + "|" +
                applicationProperties.getPayment().getMerchantKey();
        String calculatedHash = GenericUtil.calculateHash("SHA-512", calculatedUnhashed);
        if(!receivedHash.equals(calculatedHash)) {
            return "Check sum failed";
        }

        if("success".equals(paymentResponseDTO.getStatus())) {
            payment.setStatus(PaymentStatus.Success);
            order.setStatus(OrderStatus.PAID);
            this.orderRepository.save(order);
        } else if("failed".equals(paymentResponseDTO.getStatus())) {
            payment.setStatus(PaymentStatus.Failed);
        } else {
            payment.setStatus(PaymentStatus.Other);
        }
        payment.setResponseOn(Instant.now());
        payment.setPaymentResponse(paymentResponseDump);
        this.paymentRepository.save(payment);
        return paymentResponseDTO.getStatus();
    }


}
