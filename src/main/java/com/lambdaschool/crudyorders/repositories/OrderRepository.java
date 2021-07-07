package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
