package org.example.restaurantmanagementapplication.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.example.restaurantmanagementapplication.model.out.MenuItemForMenuDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MenuControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  void testDisplayMenu() {
    // When
    ResponseEntity<List<MenuItemForMenuDTO>> response = restTemplate.exchange(
        "/menu",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        }
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
  }
}
