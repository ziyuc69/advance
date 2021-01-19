package com.ziyu.mapstruct.listmapping;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author ziyu
 */
@Mapper(uses = { OrderItemMapper.class })
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );

    @Mapping(source = "orderItems", target = "orders")
    @Mapping(source = "name", target = "customerName")
    CustomerDto customerToCustomerDto(Customer customer);
}
