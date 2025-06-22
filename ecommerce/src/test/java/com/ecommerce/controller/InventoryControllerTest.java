package com.ecommerce.controller;

import com.ecommerce.entity.Item;
import com.ecommerce.service.InventoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void testSupplyEndpoint() throws Exception {
        Item mockItem = new Item();
        mockItem.setName("Mango");
        mockItem.setAvailableQuantity(100);

        when(inventoryService.createSupply("Mango", 100)).thenReturn(mockItem);

        mockMvc.perform(post("/api/inventory/supply")
                        .param("name", "Mango")
                        .param("qty", "100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mango"))
                .andExpect(jsonPath("$.availableQuantity").value(100));
    }
}