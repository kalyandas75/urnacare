package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.User;
import com.urna.urnacare.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { AddressMapper.class})
public interface UserMapper extends EntityMapper<UserDTO, User> {

}
