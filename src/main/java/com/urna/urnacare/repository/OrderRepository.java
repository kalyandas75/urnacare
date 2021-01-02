package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.domain.Order;
import com.urna.urnacare.enumeration.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByConsultationIdAndStatus(Long consultationId, OrderStatus status);
    Page<Order> findByPatientId(Long patientId, Pageable pageable);
}
