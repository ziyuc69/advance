package com.ziyu.mapstruct.listmapping;

import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-19T15:09:14+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class OrderItemMapperImpl implements OrderItemMapper {

    @Override
    public OrderItemDto orderToOrderDto(OrderItem orderItem) {
        if ( orderItem == null ) {
            return null;
        }

        OrderItemDto orderItemDto = new OrderItemDto();

        orderItemDto.name = orderItem.getName();
        orderItemDto.quantity = orderItem.getQuantity();

        return orderItemDto;
    }
}
