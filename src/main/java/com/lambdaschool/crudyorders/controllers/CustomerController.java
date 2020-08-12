package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.CustomerService;
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
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //localhost:5280/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllOrders() {
        List<Customer> allOrders = customerService.getAllOrders();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    //localhost:5280/customers/:custcode
    @GetMapping(value = "/{custid}", produces = "application/json")
    public ResponseEntity<?> findTargetedCustomer(@PathVariable long custid) {
        Customer specCustomer = customerService.getThatCustomer(custid);
        return new ResponseEntity<>(specCustomer, HttpStatus.OK);
    }

    //Test Only
    //localhost:5280/customers/:custcode(notfound)
    //Test for custcode # 77 was successful

    //localhost:5280/customers/namelike/:substring
    @GetMapping(value = "/namelike/{partial}", produces = "application/json")
    public ResponseEntity<?> findAllNames(@PathVariable String partial) {
        List<Customer> partCustomer = customerService.getAllPartialNames(partial);
        return new ResponseEntity<>(partCustomer, HttpStatus.OK);
    }

    //Test Only
    //localhost:5280/customers/namelike/:substring(notfound)
    //Test for partial custname of "xzq" was successful

    //POST - localhost:5280/customers/customer
    @PostMapping(value = "/customer", consumes = "application/json")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer newCustomer) {
        newCustomer.setCustcode(0);
        newCustomer = customerService.save(newCustomer);

        HttpHeaders customerHeader = new HttpHeaders();
        URI newCustURI = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + newCustomer.getCustcode())
                .build()
                .toUri();
        customerHeader.setLocation(newCustURI);

        return new ResponseEntity<>(null, customerHeader, HttpStatus.CREATED);
    }

    //PUT - localhost:5280/customers/customer/{custcode}

    //PATCH - localhost:5280/customers/customer/{custcode}

    //DELETE - localhost:5280/customers/customer/{custcode}
}
