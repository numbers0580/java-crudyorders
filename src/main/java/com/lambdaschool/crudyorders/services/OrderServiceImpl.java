package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderrepos;

    @Override
    public Order getReceipt(long receipt) {
        return orderrepos.findById(receipt).orElseThrow(() -> new EntityNotFoundException("No soup for you!"));
    }

    @Override
    public List<Order> getOrdersByAdvance() {
        List<Order> orderList = new ArrayList<>();
        orderrepos.findAll().iterator().forEachRemaining(orderList::add);
        return orderList;
    }
}
