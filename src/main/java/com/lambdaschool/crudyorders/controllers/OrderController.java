package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //localhost:5280/orders/order/:ordnum
    @GetMapping(value = "/order/{receipt}", produces = "application/json")
    public ResponseEntity<?> findOrderData(@PathVariable long receipt) {
        Order uniqueOrder = orderService.getReceipt(receipt);
        return new ResponseEntity<>(uniqueOrder, HttpStatus.OK);
    }

    //STRETCH
    //localhost:5280/orders/advanceamount
    @GetMapping(value = "/advanceamount", produces = "application/json")
    public ResponseEntity<?> findByAdvance() {
        List<Order> advanceOrder = orderService.getOrdersByAdvance();
        return new ResponseEntity<>(advanceOrder, HttpStatus.OK);
    }

    //POST - localhost:5280/orders/order
    @PostMapping(value = "/order", consumes = "application/json")
    public ResponseEntity<?> createOrder(@Valid @RequestBody Order createdOrder) {
        createdOrder.setOrdnum(0);
        createdOrder = orderService.saveOrder(createdOrder);

        HttpHeaders orderHeader = new HttpHeaders();
        URI orderURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + createdOrder.getOrdnum())
                .build()
                .toUri();
        orderHeader.setLocation(orderURI);

        return new ResponseEntity<>(null, orderHeader, HttpStatus.CREATED);
    }

    //PUT - localhost:5280/orders/order/{ordnum}
    @PutMapping(value = "/order/{orderid}", consumes = "application/json")
    public ResponseEntity<?> updatingSale(@Valid @RequestBody Order updatedOrder, @PathVariable long orderid) {
        updatedOrder.setOrdnum(orderid);
        orderService.saveOrder(updatedOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //DELETE - localhost:5280/orders/order/{ordnum}
    @DeleteMapping(value = "/order/{removeorderid}")
    public ResponseEntity<?> refundedSale(@PathVariable long removeorderid) {
        orderService.deleteOrder(removeorderid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
