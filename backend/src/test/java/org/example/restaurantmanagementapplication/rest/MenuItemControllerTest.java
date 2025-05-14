package org.example.restaurantmanagementapplication.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.List;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.mapper.MenuItemMapper;
import org.example.restaurantmanagementapplication.model.out.MenuItemDTO;
import org.example.restaurantmanagementapplication.service.MenuItemService;
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
@Import(MenuItemControllerTestConfig.class)
class MenuItemControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MenuItemService menuItemService;

  @Autowired private MenuItemMapper menuItemMapper;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testGetAllMenuItems_withItems() throws Exception {
    var menuItem1 = new MenuItem();
    menuItem1.setId(1L);
    menuItem1.setName("Pizza");

    var menuItem2 = new MenuItem();
    menuItem2.setId(2L);
    menuItem2.setName("Burger");

    var menuItemDTO1 = new MenuItemDTO(1L, "Pizza", null, null, true);
    var menuItemDTO2 = new MenuItemDTO(2L, "Burger", null, null, true);

    when(menuItemService.findAll()).thenReturn(List.of(menuItem1, menuItem2));
    when(menuItemMapper.toDTO(menuItem1)).thenReturn(menuItemDTO1);
    when(menuItemMapper.toDTO(menuItem2)).thenReturn(menuItemDTO2);

    mockMvc
        .perform(get("/menu-items"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Pizza"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("Burger"));
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testGetAllMenuItems_noItems() throws Exception {
    when(menuItemService.findAll()).thenReturn(List.of());

    mockMvc
        .perform(get("/menu-items"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testGetMenuItemById_found() throws Exception {
    var menuItem = new MenuItem();
    menuItem.setId(1L);
    menuItem.setName("Pizza");

    var menuItemDTO = new MenuItemDTO(1L, "Pizza", null, null, true);

    when(menuItemService.findById(1L)).thenReturn(menuItem);
    when(menuItemMapper.toDTO(menuItem)).thenReturn(menuItemDTO);

    mockMvc
        .perform(get("/menu-items/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Pizza"));
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testGetMenuItemById_notFound() throws Exception {
    when(menuItemService.findById(1L)).thenReturn(null);

    mockMvc.perform(get("/menu-items/1")).andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testCreateMenuItem() throws Exception {
    var menuItem = new MenuItem();
    menuItem.setId(1L);
    menuItem.setName("Pizza");
    menuItem.setPrice(new BigDecimal("10.00"));

    var menuItemDTO = new MenuItemDTO(1L, "Pizza", null, new BigDecimal("10.00"), true);

    when(menuItemMapper.toEntity(any(MenuItemDTO.class))).thenReturn(menuItem);
    when(menuItemService.save(any(MenuItem.class))).thenReturn(menuItem);
    when(menuItemMapper.toDTO(menuItem)).thenReturn(menuItemDTO);

    mockMvc
        .perform(
            post("/menu-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuItemDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Pizza"))
        .andExpect(jsonPath("$.price").value(10.00));
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testUpdateMenuItem_found() throws Exception {
    var menuItem = new MenuItem();
    menuItem.setId(1L);
    menuItem.setName("Pizza");
    menuItem.setPrice(new BigDecimal("10.00"));

    var menuItemDTO = new MenuItemDTO(1L, "Pizza", null, new BigDecimal("10.00"), true);

    when(menuItemMapper.toEntity(any(MenuItemDTO.class))).thenReturn(menuItem);
    when(menuItemService.findById(1L)).thenReturn(menuItem);
    when(menuItemService.save(any(MenuItem.class))).thenReturn(menuItem);
    when(menuItemMapper.toDTO(menuItem)).thenReturn(menuItemDTO);

    mockMvc
        .perform(
            put("/menu-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuItemDTO)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Pizza"))
        .andExpect(jsonPath("$.price").value(10.00));
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testUpdateMenuItem_notFound() throws Exception {
    var menuItemDTO = new MenuItemDTO(1L, "Pizza", null, null, true);

    when(menuItemMapper.toEntity(any(MenuItemDTO.class))).thenReturn(new MenuItem());
    when(menuItemService.findById(1L)).thenReturn(null);

    mockMvc
        .perform(
            put("/menu-items")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuItemDTO)))
        .andExpect(status().isNotFound());
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testDeleteMenuItem_found() throws Exception {
    var menuItem = new MenuItem();
    menuItem.setId(1L);

    when(menuItemService.findById(1L)).thenReturn(menuItem);
    doNothing().when(menuItemService).deleteById(1L);

    mockMvc.perform(delete("/menu-items/1")).andExpect(status().isNoContent());
  }

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testDeleteMenuItem_notFound() throws Exception {
    when(menuItemService.findById(1L)).thenReturn(null);

    mockMvc.perform(delete("/menu-items/1")).andExpect(status().isNotFound());
  }
}
