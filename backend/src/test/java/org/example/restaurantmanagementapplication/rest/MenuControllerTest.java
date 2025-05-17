package org.example.restaurantmanagementapplication.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.example.restaurantmanagementapplication.entity.MenuItem;
import org.example.restaurantmanagementapplication.mapper.MenuMapper;
import org.example.restaurantmanagementapplication.model.out.MenuItemForMenuDTO;
import org.example.restaurantmanagementapplication.service.MenuItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@Import(MenuControllerTestConfig.class)
class MenuControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private MenuItemService menuItemService;

  @Autowired private MenuMapper menuMapper;

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testDisplayMenu_withAvailableItems() throws Exception {
    var menuItem = new MenuItem();
    menuItem.setId(1L);
    menuItem.setName("Pizza");

    var menuItemForMenuDTO = new MenuItemForMenuDTO(1L, "Pizza", null, null);

    when(menuItemService.findAvailable()).thenReturn(List.of(menuItem));
    when(menuMapper.toDTO(menuItem)).thenReturn(menuItemForMenuDTO);

    mockMvc
        .perform(get("/menu"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("Pizza"));
  }
}
