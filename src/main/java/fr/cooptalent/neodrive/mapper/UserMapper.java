package fr.cooptalent.neodrive.mapper;

import fr.cooptalent.neodrive.domain.Authority;
import fr.cooptalent.neodrive.domain.User;
import fr.cooptalent.neodrive.dto.UserDTO;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements EntityMapper<UserDTO, User> {
    @Mappings({
            @Mapping(target = "nationalityCode", source = "entity.nationality.code"),
            @Mapping(target = "nationalityName", source = "entity.nationality.name"),
            @Mapping(target = "permitCountryCode", source = "entity.permitCountry.code"),
            @Mapping(target = "permitCountryName", source = "entity.permitCountry.name")
    })
    public abstract UserDTO toDto(User entity);
    @Mappings({
            @Mapping(target = "nationality.code", source = "dto.nationalityCode"),
            @Mapping(target = "permitCountry.code", source = "dto.permitCountryCode")
    })
    public abstract User toEntity(UserDTO dto);
    @IterableMapping(elementTargetType = String.class)
    protected abstract Set<String> mapAuthoritiesToSet(Set<Authority> authorities);

    protected String mapAuthorityToString(Authority authority) {
        return authority.getName();
    }

    protected abstract Set<Authority> mapStringToSet(Set<String> authorities);

    protected Authority mapStringToAuthority(String authorityString) {
        Authority authority = new Authority();
        authority.setName(authorityString);
        return authority;
    }
}
