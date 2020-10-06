package com.urna.urnacare.repository;

import com.urna.urnacare.domain.PrescriptionDrug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionDrugMapper extends JpaRepository<PrescriptionDrug, Long> {
}
