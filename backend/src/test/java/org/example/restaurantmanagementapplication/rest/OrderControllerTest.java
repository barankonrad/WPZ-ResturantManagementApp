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
import org.example.restaurantmanagementapplication.model.in.OrderItemRequest;
import org.example.restaurantmanagementapplication.model.in.OrderRequest;
import org.example.restaurantmanagementapplication.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private MockMvc mockMvc;

  @Mock
  private OrderService orderService;

  @Mock
  private SessionManager sessionManager;

  @InjectMocks
  private OrderController orderController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
  }

  @Test
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
  void testRetrieveAllOrders_withOrdersReturned() throws Exception {
    // given
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
  void testCreateOrder_withAnonymousUser() throws Exception {
    // given
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
}
