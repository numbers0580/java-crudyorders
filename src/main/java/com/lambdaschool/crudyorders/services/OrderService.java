package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

import java.util.List;

public interface OrderService {
    Order getReceipt(long receipt);

    List<Order> getOrdersByAdvance();

    //Order save(Order order);
}
