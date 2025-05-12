package org.example.restaurantmanagementapplication.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.OrderStatus;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;
import org.example.restaurantmanagementapplication.repository.MenuItemRepository;
import org.example.restaurantmanagementapplication.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

  private final OrderRepository orderRepository;
  private final MenuItemRepository menuItemRepository;

  @Override
  public List<Order> findAll() {
    return orderRepository.findAll();
  }

  @Transactional
  public Order createOrder(OrderRequest request) {
    var order = new Order();
    order.setCreatedBy(request.getCreatedBy());
    order.setUpdatedBy(request.getCreatedBy());
    order.setStatus(OrderStatus.NEW);

    List<OrderItem> orderItems = new ArrayList<>();

    for (var orderItemRequest : request.getOrderedItems()) {
      var menuItem = menuItemRepository.findById(orderItemRequest.getMenuItemId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Menu item not found: " + orderItemRequest.getMenuItemId()));

      var orderItem = new OrderItem();
      orderItem.setOrder(order);
      orderItem.setMenuItem(menuItem);
      orderItem.setQuantity(orderItemRequest.getQuantity());
      orderItem.setPrice(menuItem.getPrice());

      orderItems.add(orderItem);
    }

    order.setItems(orderItems);

    return orderRepository.save(order);
  }

  @Override
  public Order findById(int id) {
    return orderRepository.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public Order save(Order order) {
    return orderRepository.save(order);
  }
}
