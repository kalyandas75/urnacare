package com.urna.urnacare.repository;

import com.urna.urnacare.domain.AppointmentPayment;
import com.urna.urnacare.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentPaymentRepository extends JpaRepository<AppointmentPayment, Long> {
    AppointmentPayment findOneByTransactionId(String transactionId);
}
