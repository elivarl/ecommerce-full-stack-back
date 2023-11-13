package com.icodeap.ecommerce.backend.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private Integer id;
    private LocalDateTime dateCreated;
    private List<OrderProduct> orderProducts;
    private OrderState orderState;
    private Integer userId;

    public Order() {
        orderProducts = new ArrayList<>();
    }
    public BigDecimal getTotalOrderPrice(){
        return this.orderProducts.stream().map( ordenProduct -> ordenProduct.getTotalItem() ).reduce(BigDecimal.ZERO,BigDecimal::add);
    }
}
