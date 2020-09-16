package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findOneByEmailIgnoreCase(String email);
    List<Doctor> findByPrimarySpeciality(String primarySpeciality);
}
