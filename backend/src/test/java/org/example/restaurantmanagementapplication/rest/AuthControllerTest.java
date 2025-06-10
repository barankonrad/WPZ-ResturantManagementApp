package org.example.restaurantmanagementapplication.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.example.restaurantmanagementapplication.model.in.LoginRequest;
import org.example.restaurantmanagementapplication.model.out.UserOut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthControllerTest {

  public static final String TEST_USER_EMAIL = "admin@wpz.com";
  public static final String TEST_USER_PASSWORD = "admin@wpz.com";
  @Autowired private TestRestTemplate restTemplate;

  @Test
  void testLogin_Success() {
    // Given
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail(TEST_USER_EMAIL);
    loginRequest.setPassword(TEST_USER_PASSWORD);

    // When
    ResponseEntity<UserOut> response = restTemplate.postForEntity(
        "/login",
        loginRequest,
        UserOut.class
    );

    // Then
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(TEST_USER_EMAIL, response.getBody().getEmail());
    assertNotNull(response.getBody().getRole());
  }

  @Test
  void testLogin_Failure() {
    // Given
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail(TEST_USER_EMAIL);
    loginRequest.setPassword("wrong password");

    // When
    ResponseEntity<UserOut> response = restTemplate.postForEntity(
        "/login",
        loginRequest,
        UserOut.class
    );

    // Then
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void testMe_Unauthenticated() {
    // When
    ResponseEntity<UserOut> response = restTemplate.getForEntity(
        "/me",
        UserOut.class
    );

    // Then
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
  }

  @Test
  void testMe_Authenticated() {
    LoginRequest loginRequest = new LoginRequest();
    loginRequest.setEmail(TEST_USER_EMAIL);
    loginRequest.setPassword(TEST_USER_PASSWORD);

    ResponseEntity<UserOut> loginResponse = restTemplate.postForEntity(
        "/login",
        loginRequest,
        UserOut.class
    );

    String sessionCookie = loginResponse.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
    HttpHeaders headers = new HttpHeaders();
    headers.add(HttpHeaders.COOKIE, sessionCookie);

    ResponseEntity<UserOut> meResponse = restTemplate.exchange(
        "/me",
        HttpMethod.GET,
        new HttpEntity<>(headers),
        UserOut.class
    );

    // Then
    assertEquals(HttpStatus.OK, meResponse.getStatusCode());
    assertNotNull(meResponse.getBody());
    assertEquals(TEST_USER_EMAIL, meResponse.getBody().getEmail());
    assertNotNull(meResponse.getBody().getRole());
  }
}
