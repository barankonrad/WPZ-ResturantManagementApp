package org.example.restaurantmanagementapplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.model.OrderStatus;
import org.example.restaurantmanagementapplication.model.in.OrderItemRequest;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;
import org.example.restaurantmanagementapplication.repository.MenuItemRepository;
import org.example.restaurantmanagementapplication.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

  @Mock
  private OrderRepository orderRepository;

  @Mock
  private MenuItemRepository menuItemRepository;

  @InjectMocks
  private OrderServiceImpl orderService;

  private MenuItem menuItem;

  @BeforeEach
  void setup() {
    menuItem = new MenuItem();
    menuItem.setId(1L);
    menuItem.setPrice(BigDecimal.valueOf(10.50));
  }

  @Test
  void createOrder_shouldCreateAndSaveOrder() {
    // given
    var itemRequest = new OrderItemRequest();
    itemRequest.setMenuItemId(1L);
    itemRequest.setQuantity(2);

    var request = new OrderRequest();
    request.setCreatedBy("admin");
    request.setOrderedItems(List.of(itemRequest));

    when(menuItemRepository.findById(1L)).thenReturn(Optional.of(menuItem));
    when(orderRepository.save(any(Order.class))).thenAnswer(
        invocation -> invocation.getArgument(0));

    // when
    var result = orderService.createOrder(request);

    // then
    assertNotNull(result);
    assertEquals("admin", result.getCreatedBy());
    assertEquals(OrderStatus.NEW, result.getStatus());
    assertEquals(1, result.getItems().size());

    var savedItem = result.getItems().getFirst();
    assertEquals(menuItem, savedItem.getMenuItem());
    assertEquals(2, savedItem.getQuantity());
    assertEquals(BigDecimal.valueOf(10.50), savedItem.getPrice());

    verify(orderRepository).save(any(Order.class));
    verify(menuItemRepository).findById(1L);
  }

  @Test
  void createOrder_shouldThrowException_whenMenuItemNotFound() {
    // given
    var itemRequest = new OrderItemRequest();
    itemRequest.setMenuItemId(999L);
    itemRequest.setQuantity(1);

    var request = new OrderRequest();
    request.setCreatedBy("admin");
    request.setOrderedItems(List.of(itemRequest));

    when(menuItemRepository.findById(999L)).thenReturn(Optional.empty());

    // when + then
    var ex = assertThrows(IllegalArgumentException.class,
        () -> orderService.createOrder(request));

    assertEquals("Menu item not found: 999", ex.getMessage());
    verify(menuItemRepository).findById(999L);
    verify(orderRepository, never()).save(any());
  }
}
