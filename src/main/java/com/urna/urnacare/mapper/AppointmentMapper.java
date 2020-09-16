package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Appointment;
import com.urna.urnacare.dto.AppointmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { MedicalFileMapper.class })
public abstract class AppointmentMapper implements EntityMapper<AppointmentDto, Appointment> {
    @Mappings({
            @Mapping(target = "doctorId", source = "doctor.id"),
            @Mapping(target = "doctorFirstName", source = "doctor.firstName"),
            @Mapping(target = "doctorLastName", source = "doctor.lastName"),
            @Mapping(target = "doctorQualifications", source = "doctor.qualifications"),
            @Mapping(target = "doctorPrimarySpeciality", source = "doctor.primarySpeciality"),
            @Mapping(target = "patientId", source = "patient.id"),
            @Mapping(target = "patientFirstName", source = "patient.firstName"),
    })
    public abstract AppointmentDto toDto(Appointment appointment);

    @Mappings({
            @Mapping(source = "doctorId", target = "doctor.id"),
            @Mapping(source = "patientId", target = "patient.id")
    })
    public abstract Appointment toEntity(AppointmentDto appointmentDto);
}
