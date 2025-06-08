package org.example.restaurantmanagementapplication.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.example.restaurantmanagementapplication.model.OrderStatus;
import org.example.restaurantmanagementapplication.model.in.OrderItemRequest;
import org.example.restaurantmanagementapplication.model.out.OrderDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  private static Long createdOrderId;

  @Test
  @Order(1)
  void testGetAllOrders() {
    // When
    ResponseEntity<List<OrderDto>> response = restTemplate.exchange(
        "/orders",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<OrderDto>>() {
        }
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  @Order(2)
  void testCreateOrder() {
    // Given
    List<OrderItemRequest> orderItems = new ArrayList<>();
    OrderItemRequest item = new OrderItemRequest();
    item.setMenuItemId(1L);
    item.setQuantity(2);
    orderItems.add(item);

    // When
    ResponseEntity<OrderDto> response = restTemplate.postForEntity(
        "/orders",
        orderItems,
        OrderDto.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getId());
    assertEquals(OrderStatus.NEW, response.getBody().getStatus());

    createdOrderId = response.getBody().getId();
  }

  @Test
  @Order(3)
  void testOrderStatusTransition_NewToPending() {
    // When
    ResponseEntity<OrderDto> response = restTemplate.postForEntity(
        "/orders/" + createdOrderId + "/mark-as-pending",
        null,
        OrderDto.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdOrderId, response.getBody().getId());
    assertEquals(OrderStatus.PENDING, response.getBody().getStatus());
  }

  @Test
  @Order(4)
  void testOrderStatusTransition_PendingToConfirmed() {
    // When
    ResponseEntity<OrderDto> response = restTemplate.postForEntity(
        "/orders/" + createdOrderId + "/confirm",
        null,
        OrderDto.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdOrderId, response.getBody().getId());
    assertEquals(OrderStatus.CONFIRMED, response.getBody().getStatus());
  }

  @Test
  @Order(5)
  void testOrderStatusTransition_ConfirmedToInProgress() {
    // When
    ResponseEntity<OrderDto> response = restTemplate.postForEntity(
        "/orders/" + createdOrderId + "/start-preparation",
        null,
        OrderDto.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdOrderId, response.getBody().getId());
    assertEquals(OrderStatus.IN_PROGRESS, response.getBody().getStatus());
  }

  @Test
  @Order(6)
  void testOrderStatusTransition_InProgressToReady() {
    // When
    ResponseEntity<OrderDto> response = restTemplate.postForEntity(
        "/orders/" + createdOrderId + "/mark-as-ready",
        null,
        OrderDto.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdOrderId, response.getBody().getId());
    assertEquals(OrderStatus.READY, response.getBody().getStatus());
  }

  @Test
  @Order(7)
  void testCancelOrder_InvalidTransition() {
    // When
    ResponseEntity<OrderDto> response = restTemplate.postForEntity(
        "/orders/" + createdOrderId + "/cancel",
        null,
        OrderDto.class
    );

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(OrderStatus.READY, response.getBody().getStatus());
  }

  @Test
  @Order(8)
  void testOrderStatusTransition_NonExistentOrder() {
    // When
    ResponseEntity<OrderDto> response = restTemplate.postForEntity(
        "/orders/999999/mark-as-ready",
        null,
        OrderDto.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
