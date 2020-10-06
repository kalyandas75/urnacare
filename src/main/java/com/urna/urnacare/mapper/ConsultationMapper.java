package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.dto.ConsultationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { PrescriptionDrugMapper.class})
public interface ConsultationMapper extends EntityMapper<ConsultationDto, Consultation> {
}
