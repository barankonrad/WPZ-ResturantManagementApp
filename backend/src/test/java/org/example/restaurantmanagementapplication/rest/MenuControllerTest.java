package org.example.restaurantmanagementapplication.rest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.example.restaurantmanagementapplication.entity.MenuItem;
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

  @Test
  @WithMockUser(
      username = "admin@example.com",
      roles = {"ADMIN"})
  void testDisplayMenu_withAvailableItems() throws Exception {
    // given
    var menuItem1 = new MenuItem();
    menuItem1.setId(1L);
    menuItem1.setName("Pizza");
    menuItem1.setAvailable(true);

    var menuItem2 = new MenuItem();
    menuItem2.setId(2L);
    menuItem2.setName("Burger");
    menuItem2.setAvailable(true);

    when(menuItemService.findAvailable()).thenReturn(List.of(menuItem1, menuItem2));

    // when + then
    mockMvc
        .perform(get("/menu"))
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
  void testDisplayMenu_withNoAvailableItems() throws Exception {
    // given
    when(menuItemService.findAvailable()).thenReturn(List.of());

    // when + then
    mockMvc.perform(get("/menu")).andExpect(status().isOk()).andExpect(jsonPath("$").isEmpty());
  }
}
