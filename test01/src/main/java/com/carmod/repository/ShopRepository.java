package com.carmod.repository;

import com.carmod.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Page<Shop> findByRegionCode(String regionCode, Pageable pageable);
    Page<Shop> findByBrandsContaining(String brand, Pageable pageable);
    Page<Shop> findByServicesContaining(String service, Pageable pageable);
} 