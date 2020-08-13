package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.AgentRepository;
import com.lambdaschool.crudyorders.repositories.CustomerRepository;
import com.lambdaschool.crudyorders.repositories.OrderRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomerRepository custrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Autowired
    AgentRepository agentrepos;

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

        if(customer.getCustcode() != 0) {
            custrepos.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer was not found"));

            newCust.setCustcode(customer.getCustcode());
        }

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
            newOrder.setCustomer(newCust);

            //Many to Many (Orders <-> Payments)
            newOrder.getPayments().clear();
            for(Payment p : o.getPayments()) {
                Payment newPay = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment ID: " + p.getPaymentid() + " is not found."));

                newOrder.getPayments().add(newPay); //Creating an array of payment options
            }
            newOrder.setPayments(o.getPayments());

            newCust.getOrders().add(newOrder);
        }

        //Many to Many (Agents <-> Customers)
        /*
        Agent newAgent = agentrepos.findById(newCust.getAgent().getAgentcode())
                .orElseThrow(() -> new EntityNotFoundException("Agent " + newCust.getAgent().getAgentcode() + " is not found."));

        newCust.setAgent(newAgent);
        */

        return custrepos.save(newCust);
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long id) {
        Customer currCust = custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer ID: " + id + " was not found."));

        if(customer.getCustname() != null) currCust.setCustname(customer.getCustname());
        if(customer.getCustcity() != null) currCust.setCustcity(customer.getCustcity());
        if(customer.getWorkingarea() != null) currCust.setWorkingarea(customer.getWorkingarea());
        if(customer.getCustcountry() != null) currCust.setCustcountry(customer.getCustcountry());
        if(customer.getGrade() != null) currCust.setGrade(customer.getGrade());
        if(customer.hasvalueforopeningamt) currCust.setOpeningamt(customer.getOpeningamt());
        if(customer.hasvalueforreceiveamt) currCust.setReceiveamt(customer.getReceiveamt());
        if(customer.hasvalueforpaymentamt) currCust.setPaymentamt(customer.getPaymentamt());
        if(customer.hasvalueforoutstandingamt) currCust.setOutstandingamt(customer.getOutstandingamt());
        if(customer.getPhone() != null) currCust.setPhone(customer.getPhone());
        if(customer.getAgent() != null) currCust.setAgent(customer.getAgent());

        if(customer.getOrders().size() > 0) {
            for(Order ox : customer.getOrders()) {
                Order orderItem = new Order();

                orderItem.setAdvanceamount(ox.getAdvanceamount());
                orderItem.setOrdamount(ox.getOrdamount());
                orderItem.setOrderdescription(ox.getOrderdescription());

                orderItem.setCustomer(currCust);

                orderItem.getPayments().clear();
                for(Payment pay : ox.getPayments()) {
                    Payment newPay = paymentrepos.findById(pay.getPaymentid())
                            .orElseThrow(() -> new EntityNotFoundException("Payment id was not found"));

                    orderItem.getPayments().add(newPay);
                }
                orderItem.setPayments(ox.getPayments());
                currCust.getOrders().add(orderItem);
                //currCust.getOrders().add(ox);
            }
        }

        return custrepos.save(currCust);
    }

    @Transactional
    @Override
    public void delete(long id) {
        if(custrepos.findById(id).isPresent()) {
            custrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Customer ID: " + id + " apparently escaped scheduled deletion.");
        }
    }
}
