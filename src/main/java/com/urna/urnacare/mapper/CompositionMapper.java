package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Composition;
import com.urna.urnacare.dto.CompositionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CompositionMapper extends EntityMapper<CompositionDTO, Composition> {
}
