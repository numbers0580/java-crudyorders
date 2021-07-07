package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;

import java.util.List;

public interface CustomerService {
    // Please hold for the next available operator. Your call is very important to us.
    List<Customer> getAllOrders();

    Customer getThatCustomer(long custid);

    List<Customer> getAllPartialNames(String partial);

    Customer save(Customer customer);

    Customer update(Customer customer, long id);

    void delete(long id);
}
