package com.ziyu.mapstruct.listmapping;

import java.util.List;

/**
 * @author ziyu
 */
public class CustomerDto {

    public Long id;
    public String customerName;
    public List<OrderItemDto> orders;
}
