package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Order;
import com.urna.urnacare.dto.OrderDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
    @Mappings({
            @Mapping(target = "patientId", source = "patient.id"),
            @Mapping(target = "patientFirstName", source = "patient.firstName"),
            @Mapping(target = "patientLastName", source = "patient.lastName"),
            @Mapping(target = "patientEmail", source = "patient.email"),
            @Mapping(target = "patientPhoneNumber", source = "patient.phoneNumber")
    })
    OrderDTO toDto(Order entity);
    @Mappings({
            @Mapping(source = "patientId", target = "patient.id")
    })
    Order toEntity(OrderDTO dto);
}
