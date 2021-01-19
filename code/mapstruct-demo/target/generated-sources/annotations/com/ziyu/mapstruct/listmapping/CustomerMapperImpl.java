package com.ziyu.mapstruct.listmapping;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-01-19T15:54:07+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_151 (Oracle Corporation)"
)
public class CustomerMapperImpl implements CustomerMapper {

    private final OrderItemMapper orderItemMapper = Mappers.getMapper( OrderItemMapper.class );

    @Override
    public CustomerDto customerToCustomerDto(Customer customer) {
        if ( customer == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.orders = orderItemCollectionToOrderItemDtoList( customer.getOrderItems() );
        customerDto.customerName = customer.getName();
        customerDto.id = customer.getId();

        return customerDto;
    }

    protected List<OrderItemDto> orderItemCollectionToOrderItemDtoList(Collection<OrderItem> collection) {
        if ( collection == null ) {
            return null;
        }

        List<OrderItemDto> list = new ArrayList<OrderItemDto>( collection.size() );
        for ( OrderItem orderItem : collection ) {
            list.add( orderItemMapper.orderToOrderDto( orderItem ) );
        }

        return list;
    }
}
