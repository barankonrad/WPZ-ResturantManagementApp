package org.example.restaurantmanagementapplication.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.OrderItem;
import org.example.restaurantmanagementapplication.model.OrderStatus;
import org.example.restaurantmanagementapplication.model.OrderStatusTransition;
import org.example.restaurantmanagementapplication.model.in.OrderItemUpdateRequest;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;
import org.example.restaurantmanagementapplication.repository.MenuItemRepository;
import org.example.restaurantmanagementapplication.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MenuItemRepository menuItemRepository;

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

  public Order findById(int id) {
    return orderRepository.findById(id).orElse(null);
  }

  @Transactional
  public Order save(Order order) {
    return orderRepository.save(order);
  }

  @Transactional
  public Order updateOrder(
      int id, List<OrderItemUpdateRequest> orderItems, String currentUserName) {
    var order =
        orderRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Order not found: " + id));

    if (!OrderStatusTransition.canBeUpdated(order.getStatus())) {
        throw new IllegalArgumentException(
                "Order in status " + order.getStatus().name() + " cannot be updated");
    }

    var existingItems =
        order.getItems().stream().collect(Collectors.toMap(OrderItem::getMenuItem, item -> item));

    for (var orderItemRequest : orderItems) {
      var menuItem =
          menuItemRepository
              .findById(orderItemRequest.getMenuItemId())
              .orElseThrow(
                  () ->
                      new IllegalArgumentException(
                          "Menu item not found: " + orderItemRequest.getMenuItemId()));

      if (existingItems.containsKey(menuItem)) {
        var existingItem = existingItems.get(menuItem);
        existingItem.setQuantity(orderItemRequest.getQuantity());
        existingItem.setPrice(menuItem.getPrice());
        existingItems.remove(menuItem);
      } else {
        var newItem = new OrderItem();
        newItem.setOrder(order);
        newItem.setMenuItem(menuItem);
        newItem.setQuantity(orderItemRequest.getQuantity());
        newItem.setPrice(menuItem.getPrice());
        order.getItems().add(newItem);
      }
    }

    for (var remainingItem : existingItems.values()) {
      order.getItems().remove(remainingItem);
    }

    order.setUpdatedBy(currentUserName);

    return orderRepository.save(order);
  }
}
