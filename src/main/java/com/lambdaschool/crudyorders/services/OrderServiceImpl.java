package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.OrderRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrderRepository orderrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public Order getReceipt(long receipt) {
        return orderrepos.findById(receipt).orElseThrow(() -> new EntityNotFoundException("No soup for you!"));
    }

    @Override
    public List<Order> getOrdersByAdvance() {
        List<Order> orderList = new ArrayList<>();
        orderrepos.findAll().iterator().forEachRemaining(orderList::add);
        List<Order> filteredList = new ArrayList<>();
        for(Order item : orderList) {
            if(item.getAdvanceamount() > 0.00) {
                filteredList.add(item);
            }
        }
        return filteredList;
    }

    @Transactional
    @Override
    public Order saveOrder(Order order) {
        Order newSale = new Order();

        if(order.getOrdnum() != 0) {
            orderrepos.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order ID: " + order.getOrdnum() + " was not foune."));

            newSale.setOrdnum(order.getOrdnum());
        }

        newSale.setOrdamount(order.getOrdamount());
        newSale.setAdvanceamount(order.getAdvanceamount());
        newSale.setCustomer(order.getCustomer());

        //Many to Many
        newSale.getPayments().clear();
        for(Payment p : order.getPayments()) {
            Payment newPay = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment ID: " + p.getPaymentid() + " was not found."));
            newSale.getPayments().add(newPay);
        }
        newSale.setPayments(order.getPayments());

        return orderrepos.save(newSale);
    }

    @Transactional
    @Override
    public void deleteOrder(long ordid) {
        if(orderrepos.findById(ordid).isPresent()) {
            orderrepos.deleteById(ordid);
        } else {
            throw new EntityNotFoundException("Order ID: " + ordid + " doesn't exist.");
        }
    }
}
