package com.urna.urnacare.mapper;

import com.urna.urnacare.domain.Order;
import com.urna.urnacare.dto.OrderDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = { OrderItemMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {
}
