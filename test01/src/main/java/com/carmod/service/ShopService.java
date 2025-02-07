package com.carmod.service;

import com.carmod.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopService {
    Page<Shop> findAll(Pageable pageable);
    Shop findById(Long id);
    Shop save(Shop shop);
    Shop update(Long id, Shop shop);
    void delete(Long id);
    Page<Shop> findByRegion(String regionCode, Pageable pageable);
    Page<Shop> findByBrand(String brand, Pageable pageable);
    Page<Shop> findByService(String service, Pageable pageable);
}