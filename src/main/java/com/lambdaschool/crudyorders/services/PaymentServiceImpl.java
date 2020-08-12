package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "paymentService")
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    PaymentRepository paymentrepos;
}
