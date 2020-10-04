package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Drug;
import com.urna.urnacare.dto.DrugDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { CompositionMapper.class, ManufacturerMapper.class})
public interface DrugMapper extends EntityMapper<DrugDTO, Drug> {
    @Mappings({
            @Mapping(target = "composition.id", source = "compositionId"),
            @Mapping(target = "manufacturer.id", source = "manufacturerId")
    })
    Drug toEntity(DrugDTO dto);

    @Mappings({
            @Mapping(target = "compositionId", source = "composition.id"),
            @Mapping(target = "compositionName", source = "composition.name"),
            @Mapping(target = "manufacturerId", source = "manufacturer.id"),
            @Mapping(target = "manufacturerName", source = "manufacturer.name")
    })
    DrugDTO toDto(Drug entity);
}
