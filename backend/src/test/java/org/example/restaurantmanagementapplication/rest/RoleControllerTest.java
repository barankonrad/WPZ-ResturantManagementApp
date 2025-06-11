package org.example.restaurantmanagementapplication.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.example.restaurantmanagementapplication.entity.Role;
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
class RoleControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  private static Integer createdRoleId;

  @Test
  @Order(1)
  void testRetrieveAllRoles() {
    // When
    ResponseEntity<List<Role>> response = restTemplate.exchange(
        "/roles",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        }
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isEmpty(), "Roles list should not be empty");
  }

  @Test
  @Order(2)
  void testCreateRole() {
    // Given
    Role newRole = new Role();
    newRole.setName("TEST_ROLE");

    // When
    ResponseEntity<Role> response = restTemplate.postForEntity(
        "/roles",
        newRole,
        Role.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("ROLE_TEST_ROLE", response.getBody().getName());

    createdRoleId = response.getBody().getId();
  }

  @Test
  @Order(3)
  void testRetrieveRole() {
    // When
    ResponseEntity<Role> response = restTemplate.getForEntity(
        "/roles/" + createdRoleId,
        Role.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdRoleId, response.getBody().getId());
    assertEquals("ROLE_TEST_ROLE", response.getBody().getName());
  }

  @Test
  @Order(4)
  void testReplaceRole() {
    // Given
    Role updatedRole = new Role();
    updatedRole.setId(createdRoleId);
    updatedRole.setName("UPDATED_TEST_ROLE");

    // When
    HttpEntity<Role> requestEntity = new HttpEntity<>(updatedRole);
    ResponseEntity<Role> response = restTemplate.exchange(
        "/roles",
        HttpMethod.PUT,
        requestEntity,
        Role.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdRoleId, response.getBody().getId());
    assertEquals("ROLE_UPDATED_TEST_ROLE", response.getBody().getName());
  }

  @Test
  @Order(5)
  void testUpdateRole() {
    // Given
    Role partialRole = new Role();
    partialRole.setName("PATCHED_TEST_ROLE");

    // When
    HttpEntity<Role> requestEntity = new HttpEntity<>(partialRole);
    ResponseEntity<Role> response = restTemplate.exchange(
        "/roles/" + createdRoleId,
        HttpMethod.PATCH,
        requestEntity,
        Role.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdRoleId, response.getBody().getId());
    assertEquals("ROLE_PATCHED_TEST_ROLE", response.getBody().getName());
  }

  @Test
  @Order(6)
  void testDeleteRole() {
    // When
    ResponseEntity<Void> deleteResponse = restTemplate.exchange(
        "/roles/" + createdRoleId,
        HttpMethod.DELETE,
        null,
        Void.class
    );

    // Then
    assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

    ResponseEntity<Role> getResponse = restTemplate.getForEntity(
        "/roles/" + createdRoleId,
        Role.class
    );
    assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  }

  @Test
  @Order(7)
  void testRetrieveRoleNotFound() {
    // When
    ResponseEntity<Role> response = restTemplate.getForEntity(
        "/roles/999999",
        Role.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  @Order(8)
  void testReplaceRoleNotFound() {
    // Given
    Role nonExistentRole = new Role();
    nonExistentRole.setId(999999);
    nonExistentRole.setName("NON_EXISTENT_ROLE");

    // When
    HttpEntity<Role> requestEntity = new HttpEntity<>(nonExistentRole);
    ResponseEntity<Role> response = restTemplate.exchange(
        "/roles",
        HttpMethod.PUT,
        requestEntity,
        Role.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  @Order(9)
  void testUpdateRoleNotFound() {
    // Given
    Role partialRole = new Role();
    partialRole.setName("NON_EXISTENT_ROLE");

    // When
    HttpEntity<Role> requestEntity = new HttpEntity<>(partialRole);
    ResponseEntity<Role> response = restTemplate.exchange(
        "/roles/999999",
        HttpMethod.PATCH,
        requestEntity,
        Role.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  @Order(10)
  void testDeleteRoleNotFound() {
    // When
    ResponseEntity<Void> response = restTemplate.exchange(
        "/roles/999999",
        HttpMethod.DELETE,
        null,
        Void.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }
}
