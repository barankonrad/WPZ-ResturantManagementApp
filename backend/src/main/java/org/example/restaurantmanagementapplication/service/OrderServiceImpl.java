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

/*
TODO: single interface implementation
 */
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
    /*
    TODO: please try to encapsulate it in a factory method in Order class
     */
    order.setCreatedBy(request.getCreatedBy());
    order.setUpdatedBy(request.getCreatedBy());
    order.setStatus(OrderStatus.NEW);

    List<OrderItem> orderItems = new ArrayList<>();

    for (var orderItemRequest : request.getOrderedItems()) {
      var menuItem = menuItemRepository.findById(orderItemRequest.getMenuItemId())
          .orElseThrow(() -> new IllegalArgumentException(
              "Menu item not found: " + orderItemRequest.getMenuItemId()));

      var orderItem = new OrderItem();
      orderItem.setOrder(order); // TODO: bidirectional relationship that can be avoided
      /*
      TODO: lines below can be replace with OrderItem constructor or static factory method
       */
      orderItem.setMenuItem(menuItem);
      orderItem.setQuantity(orderItemRequest.getQuantity());
      orderItem.setPrice(menuItem.getPrice());

      orderItems.add(orderItem);
    }

    order.setItems(orderItems);

    return orderRepository.save(order);
  }
}
