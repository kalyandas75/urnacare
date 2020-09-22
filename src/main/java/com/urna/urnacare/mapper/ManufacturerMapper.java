package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Manufacturer;
import com.urna.urnacare.dto.ManufacturerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ManufacturerMapper extends EntityMapper<ManufacturerDTO, Manufacturer> {
}
