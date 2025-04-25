package org.example.restaurantmanagementapplication.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import org.example.restaurantmanagementapplication.entity.Role;
import org.example.restaurantmanagementapplication.service.RoleService;
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
public class RoleRestControllerTest {

  private final ObjectMapper objectMapper = new ObjectMapper();
  private MockMvc mockMvc;
  @Mock private RoleService roleService;
  @InjectMocks private RoleRestController roleRestController;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(roleRestController).build();
  }

  @Test
  void testRetrieveAllRoles() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName("ROLE_USER");
    Role role2 = new Role();
    role2.setId(2);
    role2.setName("ROLE_ADMIN");

    when(roleService.findAll()).thenReturn(Arrays.asList(role, role2));

    mockMvc
        .perform(get("/roles"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("ROLE_USER"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("ROLE_ADMIN"));
  }

  @Test
  void testRetrieveRole() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName("ROLE_USER");

    when(roleService.findById(1)).thenReturn(role);

    mockMvc
        .perform(get("/roles/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("ROLE_USER"));
  }

  @Test
  void testRetrieveRoleNotFound() throws Exception {
    when(roleService.findById(1)).thenReturn(null);

    mockMvc.perform(get("/roles/1")).andExpect(status().isNotFound());
  }

  @Test
  void testCreateRole() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName("ROLE_USER");

    when(roleService.save(any(Role.class))).thenReturn(role);

    mockMvc
        .perform(
            post("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("ROLE_USER"));
  }

  @Test
  void testReplaceRole() throws Exception {
    Role existingRole = new Role();
    existingRole.setId(1);
    existingRole.setName("ROLE_USER");

    Role updatedRole = new Role();
    updatedRole.setId(1);
    updatedRole.setName("ROLE_ADMIN");

    when(roleService.findById(1)).thenReturn(existingRole);
    when(roleService.save(any(Role.class))).thenReturn(updatedRole);

    mockMvc
        .perform(
            put("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedRole)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("ROLE_ADMIN"));
  }

  @Test
  void testReplaceRoleNotFound() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName("ROLE_USER");

    when(roleService.findById(1)).thenReturn(null);

    mockMvc
        .perform(
            put("/roles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(role)))
        .andExpect(status().isNotFound());
  }

  @Test
  void testUpdateRole() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName("ROLE_USER");

    Role newRole = new Role();
    newRole.setName("ROLE_ADMIN");

    when(roleService.findById(1)).thenReturn(role);
    when(roleService.save(any(Role.class))).thenReturn(role);

    mockMvc
        .perform(
            patch("/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRole)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("ROLE_ADMIN"));
  }

  @Test
  void testUpdateRoleNotFound() throws Exception {
    Role newRole = new Role();
    newRole.setName("ROLE_ADMIN");

    when(roleService.findById(1)).thenReturn(null);

    mockMvc
        .perform(
            patch("/roles/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newRole)))
        .andExpect(status().isNotFound());
  }

  @Test
  void testDeleteRole() throws Exception {
    Role role = new Role();
    role.setId(1);
    role.setName("ROLE_USER");

    when(roleService.findById(1)).thenReturn(role);
    doNothing().when(roleService).deleteById(1);

    mockMvc.perform(delete("/roles/1")).andExpect(status().isNoContent());
  }

  @Test
  void testDeleteRoleNotFound() throws Exception {
    when(roleService.findById(1)).thenReturn(null);

    mockMvc.perform(delete("/roles/1")).andExpect(status().isNotFound());
  }
}
