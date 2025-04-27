package org.example.restaurantmanagementapplication.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import org.example.restaurantmanagementapplication.common.SessionManager;
import org.example.restaurantmanagementapplication.entity.Order;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.model.OrderStatus;
import org.example.restaurantmanagementapplication.model.in.OrderItemRequest;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;
import org.example.restaurantmanagementapplication.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(OrderControllerTestConfig.class)
class OrderControllerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;

  @Autowired private OrderService orderService;

  @Autowired private SessionManager sessionManager;

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testCreateOrder_withAuthenticatedUser() throws Exception {
    // given
    var itemRequest = new OrderItemRequest();
    itemRequest.setMenuItemId(1L);
    itemRequest.setQuantity(3);

    var user = new User();
    user.setEmail("admin@example.com");

    var order = new Order();
    order.setId(1L);

    when(sessionManager.getCurrentUser()).thenReturn(Optional.of(user));
    when(orderService.createOrder(any(OrderRequest.class))).thenReturn(order);

    // when + then
    mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(List.of(itemRequest))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1));
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testRetrieveAllOrders_withOrdersReturned() throws Exception {
    var order1 = new Order();
    order1.setId(1L);

    var order2 = new Order();
    order2.setId(2L);

    when(orderService.findAll()).thenReturn(List.of(order1, order2));

    // when + then
    mockMvc.perform(get("/orders")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[1].id").value(2));
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testRetrieveAllOrders_withNoOrdersReturned() throws Exception {
    // given
    when(orderService.findAll()).thenReturn(List.of());

    // when + then
    mockMvc.perform(get("/orders")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  @WithMockUser(
      username = "anonymous",
      roles = {})
  void testCreateOrder_withAnonymousUser() throws Exception {
    var itemRequest = new OrderItemRequest();
    itemRequest.setMenuItemId(1L);
    itemRequest.setQuantity(2);

    var order = new Order();
    order.setId(2L);

    when(sessionManager.getCurrentUser()).thenReturn(Optional.empty());
    when(orderService.createOrder(any(OrderRequest.class))).thenReturn(order);

    // when + then
    mockMvc.perform(post("/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(List.of(itemRequest))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(2));
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testCancelOrder() throws Exception {
    var user = new User();
    user.setEmail("admin@example.com");

    var order = new Order();
    order.setId(1L);
    order.setStatus(OrderStatus.NEW);

    when(sessionManager.getCurrentUser()).thenReturn(Optional.of(user));
    when(orderService.findById(1)).thenReturn(order);
    when(orderService.save(any(Order.class))).thenReturn(order);

    mockMvc
        .perform(post("/orders/1/cancel"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status").value("CANCELLED"));
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testCancelOrder_nonExistentOrder() throws Exception {
    when(orderService.findById(1)).thenReturn(null);

    mockMvc.perform(post("/orders/1/cancel")).andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testCancelOrder_invalidOrderStatus() throws Exception {
    var user = new User();
    user.setEmail("admin@example.com");

    var order = new Order();
    order.setId(1L);
    order.setStatus(OrderStatus.COMPLETED);

    when(sessionManager.getCurrentUser()).thenReturn(Optional.of(user));
    when(orderService.findById(1)).thenReturn(order);

    mockMvc
        .perform(post("/orders/1/cancel"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value("COMPLETED"));
  }

  @Test
  @WithMockUser(username = "user@example.com")
  void testCancelOrder_userWithoutRole() throws Exception {
    var order = new Order();
    order.setId(1L);
    order.setStatus(OrderStatus.NEW);

    when(orderService.findById(1)).thenReturn(order);

    mockMvc.perform(post("/orders/1/cancel")).andExpect(status().isForbidden());
  }
}
