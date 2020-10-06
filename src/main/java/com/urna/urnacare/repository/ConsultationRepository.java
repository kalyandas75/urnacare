package com.urna.urnacare.repository;


import com.urna.urnacare.domain.Consultation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends CrudRepository<Consultation, Long> {

}
