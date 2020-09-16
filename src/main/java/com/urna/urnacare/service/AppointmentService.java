package com.urna.urnacare.service;

import com.urna.urnacare.domain.Appointment;
import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.AppointmentDto;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.AppointmentMapper;
import com.urna.urnacare.repository.AppointmentRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final ConsultationService consultationService;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, ConsultationService consultationService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.consultationService = consultationService;
    }

    public List<AppointmentDto> pendingAppointmentsForPatient(Long patientId) {
        LocalDateTime scheduledDate = LocalDateTime.now().minusMinutes(60);
       return this.appointmentMapper.toDto(
                this.appointmentRepository.findByPatientIdAndScheduledDateGreaterThanAndConsultationIdIsNullOrderByScheduledDate(patientId, scheduledDate)
        );
    }

    public List<AppointmentDto> pendingAppointmentsForDoctor(Long doctorId) {
        LocalDateTime scheduledDate = LocalDateTime.now().minusMinutes(60);
        return this.appointmentMapper.toDto(
                this.appointmentRepository.findByDoctorIdAndScheduledDateGreaterThanAndConsultationIdIsNullOrderByScheduledDate(doctorId, scheduledDate)
        );
    }

    public List<AppointmentDto> pastAppointmentsForPatient(Long patientId) {
        LocalDateTime scheduledDate = LocalDateTime.now().minusMinutes(60);
        return this.appointmentMapper.toDto(
                this.appointmentRepository.pastAppointmentsForPatient(patientId, scheduledDate)
        );
    }

    public List<AppointmentDto> pastAppointmentsForDoctor(Long doctorId) {
        LocalDateTime scheduledDate = LocalDateTime.now().minusMinutes(60);
        return this.appointmentMapper.toDto(
                this.appointmentRepository.pastAppointmentsForDoctor(doctorId, scheduledDate)
        );
    }

    public void createConsultation(Long appointmentId, Consultation consultation) {
        Optional<Appointment> appointmentOpt = this.appointmentRepository.findById(appointmentId);
        appointmentOpt.ifPresent(appointment -> {
            Consultation consultation1 = this.consultationService.insert(consultation);
            appointment.setConsultationId(consultation1.getId());
            this.appointmentRepository.save(appointment);
        });
    }

    public Optional<Consultation> getConsultation(Long appointmentId) {
        Optional<Appointment> appointmentOpt = this.appointmentRepository.findById(appointmentId);
        if(appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            Long consultationId = appointment.getConsultationId();
            if(consultationId == null) {
                throw new BadRequestAlertException("Consultation not found", "appointment", "consultationNotFound");
            }
            Optional<Consultation> consultationOptional = this.consultationService.findById(appointment.getConsultationId());
            return  consultationOptional;
        }
        throw new BadRequestAlertException("Appointment not found", "appointment", "notFound");
    }
}
