package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.AppointmentRequest;
import com.urna.urnacare.dto.AppointmentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public abstract class AppointmentRequestMapper implements EntityMapper<AppointmentRequestDto, AppointmentRequest> {
    @Mappings({
            @Mapping(target = "doctorId", source = "doctor.id"),
            @Mapping(target = "doctorFirstName", source = "doctor.firstName"),
            @Mapping(target = "doctorLastName", source = "doctor.lastName"),
            @Mapping(target = "doctorQualifications", source = "doctor.qualifications"),
            @Mapping(target = "doctorPrimarySpeciality", source = "doctor.primarySpeciality"),
            @Mapping(target = "patientId", source = "patient.id"),
            @Mapping(target = "patientLastName", source = "patient.lastName"),
    })
    public abstract AppointmentRequestDto toDto(AppointmentRequest entity);

    @Mappings({
            @Mapping(source = "doctorId", target = "doctor.id"),
            @Mapping(source = "patientId", target = "patient.id")
    })
    public abstract AppointmentRequest toEntity(AppointmentRequestDto dto);
}
