package com.urna.urnacare.repository;

import com.urna.urnacare.domain.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends CrudRepository<Appointment,Long> {
	@Query("SELECT a FROM Appointment a where a.uniquieKey=?1")
	public Iterable<Appointment> getAppointmentByUniquieKey(String uniquieKey);
	
	@Query("SELECT a FROM Appointment a where a.uniquieKey=?1 and a.id=?2")
	public Iterable<Appointment> getAppointmentByUniquieKeyAndId(String uniquieKey,Long id);

	List<Appointment> findByPatientIdAndScheduledDateGreaterThanAndConsultationIdIsNullOrderByScheduledDate(Long patientId, LocalDateTime scheduledDate);
	List<Appointment> findByDoctorIdAndScheduledDateGreaterThanAndConsultationIdIsNullOrderByScheduledDate(Long doctorId, LocalDateTime scheduledDate);

	@Query("select a from Appointment a where a.patient.id = :patientId and " +
			"(a.consultationId is not null or a.scheduledDate < :scheduledDate) " +
			"order by a.scheduledDate desc")
	List<Appointment> pastAppointmentsForPatient(@Param("patientId") Long patientId, @Param("scheduledDate") LocalDateTime scheduledDate);
	@Query("select a from Appointment a where a.doctor.id = :doctorId and " +
			"(a.consultationId is not null or a.scheduledDate < :scheduledDate) " +
			"order by a.scheduledDate desc")
	List<Appointment> pastAppointmentsForDoctor(@Param("doctorId") Long doctorId, @Param("scheduledDate") LocalDateTime scheduledDate);

	Appointment findByConsultationId(@Param("consultationId") Long consultationId);
}
