package org.example.restaurantmanagementapplication.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import org.example.restaurantmanagementapplication.model.out.MenuItemDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MenuItemControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  private static Long createdMenuItemId;

  @Test
  @Order(1)
  void testGetAllMenuItems() {
    // When
    ResponseEntity<List<MenuItemDTO>> response = restTemplate.exchange(
        "/menu-items",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        }
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }

  @Test
  @Order(2)
  void testCreateMenuItem() {
    // Given
    MenuItemDTO newMenuItem = new MenuItemDTO(
        null,
        "Test Pizza",
        "A test pizza for E2E testing",
        new BigDecimal("12.99"),
        true,
        "imageUrl"
    );

    // When
    ResponseEntity<MenuItemDTO> response = restTemplate.postForEntity(
        "/menu-items",
        newMenuItem,
        MenuItemDTO.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getId());
    assertEquals("Test Pizza", response.getBody().getName());
    assertEquals("A test pizza for E2E testing", response.getBody().getDescription());
    assertEquals(0, new BigDecimal("12.99").compareTo(response.getBody().getPrice()));
    assertEquals("imageUrl", response.getBody().getImageUrl());
    assertTrue(response.getBody().isAvailable());

    createdMenuItemId = response.getBody().getId();
  }

  @Test
  @Order(3)
  void testGetMenuItemById() {
    // When
    ResponseEntity<MenuItemDTO> response = restTemplate.getForEntity(
        "/menu-items/" + createdMenuItemId,
        MenuItemDTO.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdMenuItemId, response.getBody().getId());
    assertEquals("Test Pizza", response.getBody().getName());
    assertEquals("imageUrl", response.getBody().getImageUrl());
  }

  @Test
  @Order(4)
  void testUpdateMenuItem() {
    // Given
    MenuItemDTO updatedMenuItem = new MenuItemDTO(
        createdMenuItemId,
        "Updated Test Pizza",
        "An updated test pizza",
        new BigDecimal("14.99"),
        true,
        "updatedImageUrl"
    );

    // When
    HttpEntity<MenuItemDTO> requestEntity = new HttpEntity<>(updatedMenuItem);
    ResponseEntity<MenuItemDTO> response = restTemplate.exchange(
        "/menu-items",
        HttpMethod.PUT,
        requestEntity,
        MenuItemDTO.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdMenuItemId, response.getBody().getId());
    assertEquals("Updated Test Pizza", response.getBody().getName());
    assertEquals("An updated test pizza", response.getBody().getDescription());
    assertEquals(0, new BigDecimal("14.99").compareTo(response.getBody().getPrice()));
    assertEquals("updatedImageUrl", response.getBody().getImageUrl());
  }

  @Test
  @Order(5)
  void testDeleteMenuItem() {
    // When
    ResponseEntity<Void> deleteResponse = restTemplate.exchange(
        "/menu-items/" + createdMenuItemId,
        HttpMethod.DELETE,
        null,
        Void.class
    );

    // Then
    assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

    ResponseEntity<MenuItemDTO> getResponse = restTemplate.getForEntity(
        "/menu-items/" + createdMenuItemId,
        MenuItemDTO.class
    );
    assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  }

  @Test
  @Order(6)
  void testGetMenuItemById_notFound() {
    // When
    ResponseEntity<MenuItemDTO> response = restTemplate.getForEntity(
        "/menu-items/999999",
        MenuItemDTO.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
