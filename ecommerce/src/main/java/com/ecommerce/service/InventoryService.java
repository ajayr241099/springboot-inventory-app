package com.ecommerce.service;

import com.ecommerce.entity.Item;
import com.ecommerce.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service

public class InventoryService {
    @Autowired private ItemRepository repo;
    @Autowired
    private RedisTemplate<String, Integer> redisTemplate;


    @Transactional
    public synchronized Item createSupply(String name, int qty) {
        Item item = repo.findByName(name).orElse(new Item());
        item.setName(name);
        item.setAvailableQuantity(item.getAvailableQuantity() + qty);
        return repo.save(item);
    }

    @Transactional
    public synchronized boolean reserveItem(Long id, int qty) {
        Item item = repo.findById(id).orElseThrow();
        if (item.getAvailableQuantity() >= qty) {
            item.setAvailableQuantity(item.getAvailableQuantity() - qty);
            item.setReservedQuantity(item.getReservedQuantity() + qty);
            repo.save(item);
            redisTemplate.opsForValue().set("item:" + id + ":available", item.getAvailableQuantity());
            return true;
        }
        return false;
    }

    @Transactional
    public synchronized boolean cancelReservation(Long id, int qty) {
        Item item = repo.findById(id).orElseThrow();
        if (item.getReservedQuantity() >= qty) {
            item.setReservedQuantity(item.getReservedQuantity() - qty);
            item.setAvailableQuantity(item.getAvailableQuantity() + qty);
            repo.save(item);
            redisTemplate.opsForValue().set("item:" + id + ":available", item.getAvailableQuantity());
            return true;
        }
        return false;
    }

    public int checkAvailability(Long id) {
        Integer cached = redisTemplate.opsForValue().get("item:" + id + ":available");
        if (cached != null) return cached;
        Item item = repo.findById(id).orElseThrow();
        redisTemplate.opsForValue().set("item:" + id + ":available", item.getAvailableQuantity());
        return item.getAvailableQuantity();
    }
}
