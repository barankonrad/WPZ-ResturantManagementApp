package org.example.restaurantmanagementapplication.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.model.in.RegisterRequest;
import org.example.restaurantmanagementapplication.model.out.UserOut;
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
class UserControllerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  private static Integer createdUserId;
  private static final String TEST_EMAIL = "test.e2e@example.com";
  private static final String TEST_PASSWORD = "password123";
  private static final String TEST_ROLE = "USER";
  private static final String RESULT_TEST_ROLE = "ROLE_USER";

  @Test
  @Order(1)
  void testRetrieveAllUsers() {
    // When
    ResponseEntity<List<User>> response = restTemplate.exchange(
        "/users",
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<>() {
        }
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertFalse(response.getBody().isEmpty(), "Users list should not be empty");
  }

  @Test
  @Order(2)
  void testCreateUser() {
    // Given
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setEmail(TEST_EMAIL);
    registerRequest.setPassword(TEST_PASSWORD);
    registerRequest.setRole(TEST_ROLE);

    // When
    ResponseEntity<UserOut> response = restTemplate.postForEntity(
        "/users",
        registerRequest,
        UserOut.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertNotNull(response.getBody().getId());
    assertEquals(TEST_EMAIL, response.getBody().getEmail());
    assertEquals(TEST_ROLE, response.getBody().getRole());

    createdUserId = response.getBody().getId();
  }

  @Test
  @Order(3)
  void testRetrieveUser() {
    // When
    ResponseEntity<User> response = restTemplate.getForEntity(
        "/users/" + createdUserId,
        User.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdUserId, response.getBody().getId());
    assertEquals(TEST_EMAIL, response.getBody().getEmail());
    assertNotNull(response.getBody().getRole());
    assertEquals(RESULT_TEST_ROLE, response.getBody().getRole().getName());
  }

  @Test
  @Order(4)
  void testUpdateUser() {
    // Given
    ResponseEntity<User> getResponse = restTemplate.getForEntity(
        "/users/" + createdUserId,
        User.class
    );
    User existingUser = getResponse.getBody();

    User updatedUser = new User();
    updatedUser.setId(createdUserId);
    updatedUser.setEmail("updated." + TEST_EMAIL);
    updatedUser.setPassword(TEST_PASSWORD);
    updatedUser.setRole(existingUser.getRole()); // Keep the same role

    // When
    HttpEntity<User> requestEntity = new HttpEntity<>(updatedUser);
    ResponseEntity<User> response = restTemplate.exchange(
        "/users",
        HttpMethod.PUT,
        requestEntity,
        User.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdUserId, response.getBody().getId());
    assertEquals("updated." + TEST_EMAIL, response.getBody().getEmail());
    assertNotNull(response.getBody().getRole());
    assertEquals(RESULT_TEST_ROLE, response.getBody().getRole().getName());
  }

  @Test
  @Order(5)
  void testPatchUser() {
    // Given
    User partialUser = new User();
    partialUser.setEmail("patched." + TEST_EMAIL);

    // When
    HttpEntity<User> requestEntity = new HttpEntity<>(partialUser);
    ResponseEntity<User> response = restTemplate.exchange(
        "/users/" + createdUserId,
        HttpMethod.PATCH,
        requestEntity,
        User.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(createdUserId, response.getBody().getId());
    assertEquals("patched." + TEST_EMAIL, response.getBody().getEmail());
    assertNotNull(response.getBody().getRole());
    assertEquals(RESULT_TEST_ROLE, response.getBody().getRole().getName());
  }

  @Test
  @Order(6)
  void testDeleteUser() {
    // When
    ResponseEntity<Void> deleteResponse = restTemplate.exchange(
        "/users/" + createdUserId,
        HttpMethod.DELETE,
        null,
        Void.class
    );

    // Then
    assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

    ResponseEntity<User> getResponse = restTemplate.getForEntity(
        "/users/" + createdUserId,
        User.class
    );
    assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
  }

  @Test
  @Order(7)
  void testRetrieveUserNotFound() {
    // When
    ResponseEntity<User> response = restTemplate.getForEntity(
        "/users/999999",
        User.class
    );

    // Then
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  @Order(8)
  void testCreateUserWithInvalidRole() {
    // Given
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setEmail("invalid.role@example.com");
    registerRequest.setPassword("password123");
    registerRequest.setRole("INVALID_ROLE"); // This role doesn't exist

    // When
    ResponseEntity<Object> response = restTemplate.postForEntity(
        "/users",
        registerRequest,
        Object.class
    );

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  @Order(9)
  void testCreateUserWithNullProperties() {
    // Given
    RegisterRequest registerRequest = new RegisterRequest();
    registerRequest.setEmail("null.properties@example.com");
    registerRequest.setPassword("password123");

    // When
    ResponseEntity<Object> response = restTemplate.postForEntity(
        "/users",
        registerRequest,
        Object.class
    );

    // Then
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
