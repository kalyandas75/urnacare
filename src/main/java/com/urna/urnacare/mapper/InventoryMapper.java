package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Drug;
import com.urna.urnacare.domain.Inventory;
import com.urna.urnacare.dto.InventoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { Drug.class})
public interface InventoryMapper extends EntityMapper<InventoryDTO, Inventory> {
    @Mappings({
            @Mapping(target = "drug.id", source = "drugId")
    })
    Inventory toEntity(InventoryDTO dto);

    @Mappings({
            @Mapping(target = "drugId", source = "drug.id"),
            @Mapping(target = "brand", source = "drug.brand"),
            @Mapping(target = "compositionId", source = "drug.composition.id"),
            @Mapping(target = "compositionName", source = "drug.composition.name"),
            @Mapping(target = "manufacturerId", source = "drug.manufacturer.id"),
            @Mapping(target = "manufacturerName", source = "drug.manufacturer.name"),
            @Mapping(target = "formulation", source = "drug.formulation"),
            @Mapping(target = "prescriptionRequired", source = "drug.prescriptionRequired"),
            @Mapping(target = "strength", source = "drug.strength")
    })
    InventoryDTO toDto(Inventory entity);
}
