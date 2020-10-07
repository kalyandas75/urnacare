package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Consultation;
import com.urna.urnacare.dto.ConsultationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { PrescriptionDrugMapper.class})
public interface ConsultationMapper extends EntityMapper<ConsultationDto, Consultation> {
}
