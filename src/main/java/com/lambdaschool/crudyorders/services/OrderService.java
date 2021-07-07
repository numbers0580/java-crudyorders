package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

import java.util.List;

public interface OrderService {
    Order getReceipt(long receipt);

    List<Order> getOrdersByAdvance();

    //I found that I couldn't name this "save()" like I did for customerService
    Order saveOrder(Order order);

    //I found that I couldn't name this "delete()" like the one for customerService
    void deleteOrder(long ordid);
}
