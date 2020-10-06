package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.PrescriptionDrug;
import com.urna.urnacare.dto.PrescriptionDrugDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { DrugMapper.class})
public interface PrescriptionDrugMapper extends EntityMapper<PrescriptionDrugDTO, PrescriptionDrug> {

    @Mappings({
            @Mapping(target = "drug.id", source = "drugId")
    })
    @Override
    PrescriptionDrug toEntity(PrescriptionDrugDTO dto);

    @Mappings({
            @Mapping(target = "drugId", source = "drug.id"),
            @Mapping(target = "drugBrand", source = "drug.brand"),
            @Mapping(target = "drugStrength", source = "drug.strength"),
            @Mapping(target = "drugCompositionName", source = "drug.composition.name"),
            @Mapping(target = "drugFormulation", source = "drug.formulation")
    })
    @Override
    PrescriptionDrugDTO toDto(PrescriptionDrug entity);
}
