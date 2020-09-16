package com.urna.urnacare.repository;

import com.urna.urnacare.domain.MedicalFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalFileRepository extends JpaRepository<MedicalFile, Long> {
}
