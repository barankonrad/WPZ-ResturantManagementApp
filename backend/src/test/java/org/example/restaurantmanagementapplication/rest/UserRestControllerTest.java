package org.example.restaurantmanagementapplication.rest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.entity.User;
import org.example.restaurantmanagementapplication.model.in.RegisterRequest;
import org.example.restaurantmanagementapplication.service.RoleService;
import org.example.restaurantmanagementapplication.service.UserService;
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
public class UserRestControllerTest {

  public static final String TEST_MAIL = "test@example.com";
  public static final String TEST_PASSWORD = "password";
  public static final String TEST_ROLE_USER = "ROLE_USER";
  private final ObjectMapper objectMapper = new ObjectMapper();
  private MockMvc mockMvc;
  @Mock
  private UserService userService;
  @Mock
  private RoleService roleService;
  @InjectMocks
  private UserRestController userRestController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(userRestController).build();
  }

  @Test
  void testRetrieveAllUsers() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName(TEST_ROLE_USER);

    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);
    user.setPassword(TEST_PASSWORD);
    user.setRole(role);

    User user2 = new User();
    user2.setId(2);
    user2.setEmail("test2@example.com");
    user2.setPassword("password2");
    user2.setRole(role);

    when(userService.findAll()).thenReturn(List.of(user, user2));

    mockMvc
        .perform(get("/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].email").value(TEST_MAIL))
        .andExpect(jsonPath("$[0].password").value(TEST_PASSWORD))
        .andExpect(jsonPath("$[0].role.id").value(1))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].email").value("test2@example.com"))
        .andExpect(jsonPath("$[1].password").value("password2"))
        .andExpect(jsonPath("$[1].role.id").value(1));
  }

  @Test
  void testRetrieveUser() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName(TEST_ROLE_USER);

    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);
    user.setPassword(TEST_PASSWORD);
    user.setRole(role);

    when(userService.findById(1)).thenReturn(user);

    mockMvc
        .perform(get("/users/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.email").value(TEST_MAIL))
        .andExpect(jsonPath("$.password").value(TEST_PASSWORD))
        .andExpect(jsonPath("$.role.id").value(1));
  }

  @Test
  void testRetrieveUserNotFound() throws Exception {
    when(userService.findById(1)).thenReturn(null);

    mockMvc.perform(get("/users/1")).andExpect(status().isNotFound());
  }

  @Test
  void testCreateUser() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName(TEST_ROLE_USER);

    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);
    user.setPassword(TEST_PASSWORD);
    user.setRole(role);

    RegisterRequest registerRequest = new RegisterRequest(
        user.getEmail(),
        user.getPassword(),
        user.getRole().getName()
    );

    when(roleService.findByName(TEST_ROLE_USER)).thenReturn(role);
    when(userService.save(any(User.class))).thenReturn(user);

    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.email").value(TEST_MAIL))
        .andExpect(jsonPath("$.role").value(TEST_ROLE_USER))
        .andExpect(jsonPath("$.password").doesNotExist());
  }

  @Test
  void testCreateUserRoleNotFound() throws Exception {
    RegisterRequest registerRequest = new RegisterRequest(TEST_MAIL, TEST_PASSWORD, TEST_ROLE_USER);
    when(roleService.findByName(TEST_ROLE_USER)).thenReturn(null);

    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Could not find role %s".formatted(TEST_ROLE_USER)));
  }

  @Test
  void testCreateUserWithNullProperties() throws Exception {
    RegisterRequest registerRequest = new RegisterRequest(TEST_MAIL, TEST_PASSWORD, null);

    mockMvc
        .perform(
            post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("All fields are required"));
  }

  @Test
  void testUpdateUser() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName(TEST_ROLE_USER);

    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);
    user.setRole(role);
    user.setPassword(TEST_PASSWORD);

    when(userService.findById(1)).thenReturn(user);
    when(roleService.findById(1)).thenReturn(role);
    when(userService.save(any(User.class))).thenReturn(user);

    mockMvc
        .perform(
            put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.email").value(TEST_MAIL))
        .andExpect(jsonPath("$.password").value(TEST_PASSWORD));
  }

  @Test
  void testUpdateUserNotFound() throws Exception {
    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);

    when(userService.findById(1)).thenReturn(null);

    mockMvc
        .perform(
            put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isNotFound());
  }

  @Test
  void testUpdateUserRoleNotFound() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName(TEST_ROLE_USER);

    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);
    user.setRole(role);
    user.setPassword(TEST_PASSWORD);

    when(userService.findById(1)).thenReturn(user);
    when(roleService.findById(1)).thenReturn(null);

    mockMvc
        .perform(
            put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Could not find role 1"));
  }

  @Test
  void testUpdateUserWithNullProperties() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName(TEST_ROLE_USER);

    User existingUser = new User();
    existingUser.setId(1);
    existingUser.setEmail(TEST_MAIL);
    existingUser.setPassword(TEST_PASSWORD);
    existingUser.setRole(role);

    User userWithNullProperties = new User();
    userWithNullProperties.setId(1);

    when(userService.findById(1)).thenReturn(existingUser);

    mockMvc
        .perform(
            put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userWithNullProperties)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("All fields are required"));
  }

  @Test
  void testPatchUser() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName(TEST_ROLE_USER);

    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);
    user.setRole(role);

    User newUser = new User();
    newUser.setEmail("new@example.com");

    when(userService.findById(1)).thenReturn(user);
    when(userService.save(any(User.class))).thenReturn(user);

    mockMvc
        .perform(
            patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.email").value("new@example.com"));
  }

  @Test
  void testPatchUserNotFound() throws Exception {
    User newUser = new User();
    newUser.setEmail("new@example.com");

    when(userService.findById(1)).thenReturn(null);

    mockMvc
        .perform(
            patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isNotFound());
  }

  @Test
  void testPatchUserWithNonExistentRole() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName(TEST_ROLE_USER);

    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);
    user.setRole(role);

    Role newRole = new Role();
    newRole.setId(999);
    newRole.setName("ROLE_TEST");

    User newUser = new User();
    newUser.setRole(newRole);

    when(userService.findById(1)).thenReturn(user);
    when(roleService.findById(999)).thenReturn(null);

    mockMvc
        .perform(
            patch("/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newUser)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("Could not find role 999"));
  }

  @Test
  void testDeleteUser() throws Exception {
    User user = new User();
    user.setId(1);
    user.setEmail(TEST_MAIL);

    when(userService.findById(1)).thenReturn(user);
    doNothing().when(userService).deleteById(1);

    mockMvc.perform(delete("/users/1")).andExpect(status().isNoContent());
  }

  @Test
  void testDeleteUserNotFound() throws Exception {
    when(userService.findById(1)).thenReturn(null);

    mockMvc.perform(delete("/users/1")).andExpect(status().isNotFound());
  }
}
