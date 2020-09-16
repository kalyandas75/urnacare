package com.urna.urnacare.service;

import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

@Service
public class ConsultationService {

    ConsultationRepository consultationRepository;
	
	@Autowired
	public void setConsultationRepository(ConsultationRepository consultationRepository) {
		this.consultationRepository = consultationRepository;
	}
	
	public Iterable<Consultation> findAll() {
		return consultationRepository.findAll();
	}

	public Optional<Consultation> findById(Long id) {
		return consultationRepository.findById(id);
	}

	public Consultation insert(Consultation consultation) {
		updateCreateTimeStamp(consultation);
		return consultationRepository.save(consultation);
	}
	
	public Consultation update(Consultation consultation) {
		updateRespondedTimeStamp(consultation);
		return consultationRepository.save(consultation);
	}

	public void delete(Consultation consultation) {
		consultationRepository.delete(consultation);	
	}

	public void deleteById(Long id) {
		consultationRepository.deleteById(id);
		
	}
	
	private void updateCreateTimeStamp(@Valid Consultation consultation) {
		Calendar cal= Calendar.getInstance();
		Date now = cal.getTime();
		Timestamp createdOn= new Timestamp(now.getTime());
		consultation.setCreatedOn(createdOn);
	}

	
	
	private void updateRespondedTimeStamp(@Valid Consultation consultation) {
		Calendar cal= Calendar.getInstance();
		Date now = cal.getTime();
		Timestamp lastRespondedOn= new Timestamp(now.getTime());
		consultation.setLastRespondedOn(lastRespondedOn);
	}

	public Iterable<Consultation> findAllConsultationByPatientId(Long cratedByPatientId) {
		return consultationRepository.findAllConsultationByPatientId(cratedByPatientId);
	}

	public Iterable<Consultation> findAllConsultationByRespondedDoctorId(Long lastrespondedByDocId) {
		return consultationRepository.findAllConsultationByRespondedDoctorId(lastrespondedByDocId);
	}

	public Iterable<Consultation> findAllConsultationBySpecialization(String specialization) {
		return consultationRepository.findAllConsultationBySpecialization(specialization);
	}

}
