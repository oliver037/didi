package com.carmod.controller;

import com.carmod.model.Shop;
import com.carmod.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shops")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    @GetMapping
    public ResponseEntity<Page<Shop>> getAllShops(Pageable pageable) {
        return ResponseEntity.ok(shopService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shop> getShopById(@PathVariable Long id) {
        return ResponseEntity.ok(shopService.findById(id));
    }

    @GetMapping("/region/{regionCode}")
    public ResponseEntity<Page<Shop>> getShopsByRegion(
            @PathVariable String regionCode,
            Pageable pageable) {
        return ResponseEntity.ok(shopService.findByRegion(regionCode, pageable));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<Page<Shop>> getShopsByBrand(
            @PathVariable String brand,
            Pageable pageable) {
        return ResponseEntity.ok(shopService.findByBrand(brand, pageable));
    }

    @GetMapping("/service/{service}")
    public ResponseEntity<Page<Shop>> getShopsByService(
            @PathVariable String service,
            Pageable pageable) {
        return ResponseEntity.ok(shopService.findByService(service, pageable));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Shop> createShop(@RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.save(shop));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Shop> updateShop(@PathVariable Long id, @RequestBody Shop shop) {
        return ResponseEntity.ok(shopService.update(id, shop));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteShop(@PathVariable Long id) {
        shopService.delete(id);
        return ResponseEntity.ok().build();
    }
} 