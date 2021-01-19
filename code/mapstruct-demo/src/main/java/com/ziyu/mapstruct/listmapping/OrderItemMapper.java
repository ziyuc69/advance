package com.ziyu.mapstruct.listmapping;

import org.mapstruct.Mapper;

/**
 * @author ziyu
 */
@Mapper
public interface OrderItemMapper {

    OrderItemDto orderToOrderDto(OrderItem orderItem);
}
