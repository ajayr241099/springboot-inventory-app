package com.ecommerce.service;

import com.ecommerce.entity.Item;
import com.ecommerce.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private ItemRepository repo;

    @Mock
    private RedisTemplate<String, Integer> redisTemplate;

    @InjectMocks
    private InventoryService service;

    public InventoryServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSupply_newItem() {
        String itemName = "Banana";
        int qty = 50;

        Item newItem = new Item();
        newItem.setName(itemName);
        newItem.setAvailableQuantity(0);

        when(repo.findByName(itemName)).thenReturn(Optional.empty());
        when(repo.save(any(Item.class))).thenAnswer(inv -> inv.getArgument(0));

        Item result = service.createSupply(itemName, qty);

        assertEquals(itemName, result.getName());
        assertEquals(qty, result.getAvailableQuantity());
        verify(repo, times(1)).save(any(Item.class));
    }
}