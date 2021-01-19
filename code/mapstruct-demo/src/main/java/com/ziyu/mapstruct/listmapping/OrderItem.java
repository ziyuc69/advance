package com.ziyu.mapstruct.listmapping;

import java.util.List;

/**
 * @author ziyu
 */
public class OrderItem {
    private String name;
    private Long quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
