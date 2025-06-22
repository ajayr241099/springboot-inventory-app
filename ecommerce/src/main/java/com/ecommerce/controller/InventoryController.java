package com.ecommerce.controller;

import com.ecommerce.entity.Item;
import com.ecommerce.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService service;

    @GetMapping("/supply")
    public ResponseEntity<Item> supplyBrowser(@RequestParam String name, @RequestParam int qty) {
        return ResponseEntity.ok(service.createSupply(name, qty));
    }


    @PostMapping("/supply")
    public ResponseEntity<Item> supply(@RequestParam String name, @RequestParam int qty) {
        return ResponseEntity.ok(service.createSupply(name, qty));
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserve(@RequestParam Long id, @RequestParam int qty) {
        boolean ok = service.reserveItem(id, qty);
        return ResponseEntity.ok(ok ? "Reserved" : "Not enough stock");
    }

    @PostMapping("/cancel")
    public ResponseEntity<String> cancel(@RequestParam Long id, @RequestParam int qty) {
        boolean ok = service.cancelReservation(id, qty);
        return ResponseEntity.ok(ok ? "Canceled" : "Not enough reserved");
    }

    @GetMapping("/availability")
    public ResponseEntity<Integer> availability(@RequestParam Long id) {
        return ResponseEntity.ok(service.checkAvailability(id));
    }
}
