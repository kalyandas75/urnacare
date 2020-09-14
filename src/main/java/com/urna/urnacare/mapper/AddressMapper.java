package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Address;
import com.urna.urnacare.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mappings({
            @Mapping(target = "user.id", source = "userId")
    })
    Address toEntity(AddressDTO dto);
    @Mappings({
            @Mapping(target = "userId", source = "user.id")
    })
    AddressDTO toDto(Address entity);
}
