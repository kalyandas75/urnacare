package com.urna.urnacare.service;

import com.urna.urnacare.domain.Appointment;
import com.urna.urnacare.domain.MedicalFile;
import com.urna.urnacare.dto.MedicalFileDto;
import com.urna.urnacare.errors.BadRequestAlertException;
import com.urna.urnacare.mapper.MedicalFileMapper;
import com.urna.urnacare.repository.AppointmentRepository;
import com.urna.urnacare.repository.ConsultationRepository;
import com.urna.urnacare.repository.MedicalFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MedicalRecordService {
    private final AppointmentRepository appointmentRepository;
    private final MedicalFileRepository medicalFileRepository;
    private final MedicalFileMapper medicalFileMapper;
    private final ConsultationRepository consultationRepository;

    public MedicalRecordService(AppointmentRepository appointmentRepository, MedicalFileRepository medicalFileRepository, MedicalFileMapper medicalFileMapper, ConsultationRepository consultationRepository) {
        this.appointmentRepository = appointmentRepository;
        this.medicalFileRepository = medicalFileRepository;
        this.medicalFileMapper = medicalFileMapper;
        this.consultationRepository = consultationRepository;
    }

    public List<MedicalFileDto> saveForAppointment(Long appointmentId, Long patientId, MultipartFile[] files) {
        if(files == null || files.length < 1) {
            throw new BadRequestAlertException("No files to save", "appointment", "fileNotInRequest");
        }
        Appointment appointment = this.appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new BadRequestAlertException("Appointment Not Found", "appointment", "notFound"));

        if(appointment.getPatient().getId().longValue() != patientId ) {
            throw new BadRequestAlertException("Invalid Appointment. Patient mismatch", "appointment", "patientMismatch");
        }
        return this.saveFiles(appointmentId, null, patientId, null, files);
    }

    public List<MedicalFileDto> saveForConsultation(Long consultationId, Long patientId, Long doctorId, MultipartFile[] files) {
        if(files == null || files.length < 1) {
            throw new BadRequestAlertException("No files to save", "appointment", "fileNotInRequest");
        }
        if(patientId != null && doctorId != null) {
            throw new BadRequestAlertException("Both doctor and patient cannot upload a file", "appointment", "bothPatientAndDoctorInRequest");
        }
        if(patientId == null && doctorId == null) {
            throw new BadRequestAlertException("Neither doctor nor patient in request", "appointment", "neitherPatientNorDoctorInRequest");
        }
        Appointment appointment = this.appointmentRepository.findByConsultationId(consultationId);
        if(appointment == null) {
            throw new BadRequestAlertException("Invalid Consultation Id", "appointment", "invalidConsultationId");
        }
        if(patientId != null && appointment.getPatient().getId().longValue() != patientId ) {
            throw new BadRequestAlertException("Invalid Consultation Id, patient id mismatch", "appointment", "patientIdMismatch");
        }
        if(doctorId != null && appointment.getDoctor().getId().longValue() != doctorId ) {
            throw new BadRequestAlertException("Invalid Consultation Id, doctor id mismatch", "appointment", "doctorIdMismatch");
        }
        this.consultationRepository.findById(consultationId)
                .orElseThrow(() -> new BadRequestAlertException("Consultation not found", "appointment", "consultationNotFound"));

        return this.saveFiles(appointment.getId(), consultationId, patientId, doctorId, files);

    }

    private List<MedicalFileDto> saveFiles(Long appointmentId, Long consultationId, Long patientId,
                                           Long doctorId, MultipartFile[] files) {
        Set<MedicalFile> medicalFiles = new HashSet<>();
        for (MultipartFile file: files) {
            MedicalFile medicalFile = new MedicalFile();
            medicalFile.setAppointmentId(appointmentId);
            medicalFile.setPatientId(patientId);
            medicalFile.setDoctorId(doctorId);
            medicalFile.setConsultationId(consultationId);
            try {
                medicalFile.setFile(file.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            medicalFile.setFileName(file.getOriginalFilename());
            medicalFile.setFileType(file.getContentType());
            medicalFiles.add(medicalFile);
        }
        return this.medicalFileMapper.toDto(this.medicalFileRepository.saveAll(medicalFiles));
    }
}
