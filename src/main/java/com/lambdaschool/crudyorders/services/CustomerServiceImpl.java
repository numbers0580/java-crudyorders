package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.CustomerRepository;
import com.lambdaschool.crudyorders.repositories.OrderRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository custrepos;

    @Autowired
    OrderRepository orderrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Override
    public List<Customer> getAllOrders() {
        List<Customer> list = new ArrayList<>();
        custrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Customer getThatCustomer(long custid) {
        return custrepos.findById(custid).orElseThrow(() -> new EntityNotFoundException("Customer of ID # " + custid + " was nowhere to bee seen."));
    }

    @Override
    public List<Customer> getAllPartialNames(String partial) {
        return custrepos.findByCustnameContainingIgnoringCase(partial);
    }

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCust = new Customer();

        newCust.setCustname(customer.getCustname());
        newCust.setCustcity(customer.getCustcity());
        newCust.setWorkingarea(customer.getWorkingarea());
        newCust.setCustcountry(customer.getCustcountry());
        newCust.setGrade(customer.getGrade());
        newCust.setOpeningamt(customer.getOpeningamt());
        newCust.setReceiveamt(customer.getReceiveamt());
        newCust.setPaymentamt(customer.getPaymentamt());
        newCust.setOutstandingamt(customer.getOutstandingamt());
        newCust.setPhone(customer.getPhone());
        newCust.setAgent(customer.getAgent());

        //One to Many (Customer <-> Orders)
        newCust.getOrders().clear();

        for(Order o : customer.getOrders()) {
            Order newOrder = new Order();

            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setOrderdescription(o.getOrderdescription());
            newOrder.setCustomer(o.getCustomer());

            //Many to Many (Orders <-> Payments)
            newOrder.getPayments().clear();
            for(Payment p : o.getPayments()) {
                /*
                Payment newPay = new Payment();

                newPay.setType(p.getType());
                newPay.setOrders(p.getOrders());
                */

                Payment newPay = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment ID: " + p.getPaymentid() + " is not found."));

                newOrder.getPayments().add(newPay);
            }

            newCust.getOrders().add(newOrder);
        }

        //Many to Many (Agents <-> Customers)

        return custrepos.save(customer);
    }
}
