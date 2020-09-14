package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Doctor;
import com.urna.urnacare.dto.DoctorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { AddressMapper.class})
public interface DoctorMapper extends EntityMapper<DoctorDTO, Doctor> {
}
