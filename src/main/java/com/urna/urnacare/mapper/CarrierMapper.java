package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Carrier;
import com.urna.urnacare.dto.CarrierDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CarrierMapper extends EntityMapper<CarrierDTO, Carrier> {
}
