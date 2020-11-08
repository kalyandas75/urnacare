package com.urna.urnacare.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.urna.urnacare.domain.Payment;

@Repository
public interface PaymentRepo extends CrudRepository {

    Payment findByTxnId(String transactionId);
}
