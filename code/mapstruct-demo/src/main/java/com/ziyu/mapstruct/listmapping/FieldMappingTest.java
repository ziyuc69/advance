package com.ziyu.mapstruct.listmapping;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

/**
 * @author ziyu
 */
public class FieldMappingTest {

    @Test
    public void showFieldMapping() {
        Customer customer = new Customer();
        customer.setId( 10L );
        customer.setName( "Filip" );
        OrderItem order1 = new OrderItem();
        order1.setName( "Table" );
        order1.setQuantity( 2L );
        customer.setOrderItems( Collections.singleton( order1 ) );

        CustomerDto customerDto = CustomerMapper.INSTANCE.customerToCustomerDto(customer);
        Assert.assertEquals("Filip", customerDto.customerName);
    }
}
