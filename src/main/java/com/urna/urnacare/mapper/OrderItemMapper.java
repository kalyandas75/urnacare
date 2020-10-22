package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Drug;
import com.urna.urnacare.domain.OrderItem;
import com.urna.urnacare.dto.OrderItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { DrugMapper.class})
public interface OrderItemMapper extends EntityMapper<OrderItemDTO, OrderItem> {
    @Mappings({
            @Mapping(target = "drug.id", source = "drugId")
    })
    OrderItem toEntity(OrderItemDTO dto);

    @Mappings({
            @Mapping(target = "drugId", source = "drug.id"),
            @Mapping(target = "drugBrand", source = "drug.brand"),
            @Mapping(target = "drugCompositionName", source = "drug.composition.name"),
            @Mapping(target = "drugManufacturerName", source = "drug.manufacturer.name"),
            @Mapping(target = "drugFormulation", source = "drug.formulation"),
            @Mapping(target = "drugPrescriptionRequired", source = "drug.prescriptionRequired"),
            @Mapping(target = "drugStrength", source = "drug.strength")
    })
    OrderItemDTO toDto(OrderItem entity);
}
