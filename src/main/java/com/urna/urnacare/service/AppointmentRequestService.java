package com.urna.urnacare.service;

import com.urna.urnacare.domain.Appointment;
import com.urna.urnacare.domain.AppointmentRequest;
import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.AppointmentRequestDto;
import com.urna.urnacare.enumeration.AppointmentRequestStatus;
import com.urna.urnacare.enumeration.PaymentStatus;
import com.urna.urnacare.mapper.AppointmentRequestMapper;
import com.urna.urnacare.repository.AppointmentRepository;
import com.urna.urnacare.repository.AppointmentRequestRepository;
import com.urna.urnacare.util.RandomUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = false )
public class AppointmentRequestService {

    private final AppointmentRequestRepository appointmentRequestRepository;
    private final AppointmentRepository appointmentRepository;

    private final AppointmentRequestMapper appointmentRequestMapper;

    public AppointmentRequestService(AppointmentRequestRepository appointmentRequestRepository, AppointmentRepository appointmentRepository, AppointmentRequestMapper appointmentRequestMapper) {
        this.appointmentRequestRepository = appointmentRequestRepository;
        this.appointmentRepository = appointmentRepository;
        this.appointmentRequestMapper = appointmentRequestMapper;
    }

    public AppointmentRequestDto create(AppointmentRequestDto appointmentRequestDto) {
        AppointmentRequest appointmentRequest = this.appointmentRequestMapper.toEntity(appointmentRequestDto);
        appointmentRequest.setRejectReason(null);
        appointmentRequest.setRequestStatus(AppointmentRequestStatus.REQUESTED);
        return this.appointmentRequestMapper.toDto(this.appointmentRequestRepository.save(appointmentRequest));
    }


    public Appointment approve(Long appointmentRequestId, LocalDateTime scheduleDateTime) {
        AppointmentRequest appointmentRequest = this.appointmentRequestRepository
                .findById(appointmentRequestId)
                .orElseThrow(() -> new RuntimeException("appointment request not found"));
        appointmentRequest.setRequestStatus(AppointmentRequestStatus.APPROVED);
        appointmentRequest = this.appointmentRequestRepository.save(appointmentRequest);
        Appointment appointment = new Appointment();
        Doctor doctor = new Doctor();
        doctor.setId(appointmentRequest.getDoctor().getId());
        appointment.setDoctor(doctor);
        User patient = new User();
        patient.setId(appointmentRequest.getPatient().getId());
        appointment.setPatient(patient);
        appointment.setScheduledDate(scheduleDateTime);
        appointment.setUniquieKey(RandomUtil.generateRandomAlphaNumeric(10));
        appointment.setAppointmentRequestId(appointmentRequest.getId());
        return this.appointmentRepository.save(appointment);
    }

    public void reject(Long appointmentRequestId, String rejectReason) {
        AppointmentRequest appointmentRequest = this.appointmentRequestRepository
                .findById(appointmentRequestId)
                .orElseThrow(() -> new RuntimeException("appointment request not found"));
        appointmentRequest.setRequestStatus(AppointmentRequestStatus.REJECTED);
        appointmentRequest.setRejectReason(rejectReason);
        this.appointmentRequestRepository.save(appointmentRequest);
    }

    public List<AppointmentRequestDto> getAllForPatient(Long patientId) {
        return this.appointmentRequestMapper.toDto(
                this.appointmentRequestRepository.findByPatientIdAndRequestStatusOrderByCreatedAtDesc(patientId, AppointmentRequestStatus.REQUESTED));
    }

    public List<AppointmentRequestDto> getAllForDoctor(Long doctorId) {
        return this.appointmentRequestMapper.toDto(
                this.appointmentRequestRepository.findByDoctorIdAndRequestStatusAndPaymentStatusOrderByDesiredDate(doctorId, AppointmentRequestStatus.REQUESTED, PaymentStatus.Success));
    }

}
