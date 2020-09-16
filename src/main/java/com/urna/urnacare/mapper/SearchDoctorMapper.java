package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.dto.SearchDoctorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class SearchDoctorMapper implements EntityMapper<SearchDoctorDto, Doctor> {
}
