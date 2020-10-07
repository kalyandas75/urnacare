package com.urna.urnacare.service;

import com.urna.urnacare.domain.Appointment;
import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.AppointmentDto;
import com.urna.urnacare.dto.ConsultationDto;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.AppointmentMapper;
import com.urna.urnacare.repository.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
@Slf4j
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final ConsultationService consultationService;

    public AppointmentService(AppointmentRepository appointmentRepository, AppointmentMapper appointmentMapper, ConsultationService consultationService) {
        this.appointmentRepository = appointmentRepository;
        this.appointmentMapper = appointmentMapper;
        this.consultationService = consultationService;
    }
    @Transactional(readOnly = true)
    public List<AppointmentDto> pendingAppointmentsForPatient(Long patientId) {
        LocalDateTime scheduledDate = LocalDateTime.now().minusMinutes(60);
       return this.appointmentMapper.toDto(
                this.appointmentRepository.findByPatientIdAndScheduledDateGreaterThanAndConsultationIdIsNullOrderByScheduledDate(patientId, scheduledDate)
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> pendingAppointmentsForDoctor(Long doctorId) {
        LocalDateTime scheduledDate = LocalDateTime.now().minusMinutes(60);
        return this.appointmentMapper.toDto(
                this.appointmentRepository.findByDoctorIdAndScheduledDateGreaterThanAndConsultationIdIsNullOrderByScheduledDate(doctorId, scheduledDate)
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> pastAppointmentsForPatient(Long patientId) {
        LocalDateTime scheduledDate = LocalDateTime.now().minusMinutes(60);
        return this.appointmentMapper.toDto(
                this.appointmentRepository.pastAppointmentsForPatient(patientId, scheduledDate)
        );
    }

    @Transactional(readOnly = true)
    public List<AppointmentDto> pastAppointmentsForDoctor(Long doctorId) {
        LocalDateTime scheduledDate = LocalDateTime.now().minusMinutes(60);
        return this.appointmentMapper.toDto(
                this.appointmentRepository.pastAppointmentsForDoctor(doctorId, scheduledDate)
        );
    }


    public void createConsultation(Long appointmentId, ConsultationDto consultationDto) {
        log.info("request to create consultation in service {}", consultationDto);
        Optional<Appointment> appointmentOpt = this.appointmentRepository.findById(appointmentId);

        appointmentOpt.ifPresent(appointment -> {
            log.debug("appointment found. creating consultation");
            ConsultationDto consultationDtoSaved = this.consultationService.create(consultationDto);
            appointment.setConsultationId(consultationDtoSaved.getId());
            this.appointmentRepository.save(appointment);
        });
    }
    @Transactional(readOnly = true)
    public ConsultationDto getConsultation(Long appointmentId) {
        Optional<Appointment> appointmentOpt = this.appointmentRepository.findById(appointmentId);
        if(appointmentOpt.isPresent()) {
            Appointment appointment = appointmentOpt.get();
            Long consultationId = appointment.getConsultationId();
            if(consultationId == null) {
                throw new BadRequestAlertException("Consultation not found", "appointment", "consultationNotFound");
            }
            ConsultationDto consultationDto = this.consultationService.getOne(appointment.getConsultationId());
            return  consultationDto;
        }
        throw new BadRequestAlertException("Appointment not found", "appointment", "notFound");
    }
}
