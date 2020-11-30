package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderId(Long orderId);
    Payment findOneByTransactionId(String transactionId);
}
