package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.DrugIngredient;
import com.urna.urnacare.dto.DrugIngredientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface DrugIngredientMapper extends EntityMapper<DrugIngredientDTO, DrugIngredient> {
    @Mappings({
            @Mapping(target = "drug.id", source = "drugId"),
            @Mapping(target = "ingredient.id", source = "ingredientId")
    })
    DrugIngredient toEntity(DrugIngredientDTO dto);
    @Mappings({
            @Mapping(target = "drugId", source = "drug.id"),
            @Mapping(target = "drugName", source = "drug.name"),
            @Mapping(target = "ingredientId", source = "ingredient.id"),
            @Mapping(target = "ingredientName", source = "ingredient.name")
    })
    DrugIngredientDTO toDto(DrugIngredient drugIngredient);
}
