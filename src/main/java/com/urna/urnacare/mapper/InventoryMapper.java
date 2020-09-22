package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Inventory;
import com.urna.urnacare.dto.InventoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { CompositionMapper.class, ManufacturerMapper.class})
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {
    @Mappings({
            @Mapping(target = "composition.id", source = "compositionId"),
            @Mapping(target = "manufacturer.id", source = "manufacturerId")
    })
    Inventory toEntity(InventoryDTO dto);
    @Mappings({
            @Mapping(target = "compositionId", source = "composition.id"),
            @Mapping(target = "compositionName", source = "composition.name"),
            @Mapping(target = "manufacturerId", source = "manufacturer.id"),
            @Mapping(target = "manufacturerName", source = "manufacturer.name")
    })
    InventoryDTO toDto(Inventory entity);
}
