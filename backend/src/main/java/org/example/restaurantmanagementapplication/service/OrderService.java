package org.example.restaurantmanagementapplication.service;

import java.util.List;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;

public interface OrderService {

  List<Order> findAll();

  Order createOrder(OrderRequest orderRequest);

  Order findById(int id);

  Order save(Order order);
}
