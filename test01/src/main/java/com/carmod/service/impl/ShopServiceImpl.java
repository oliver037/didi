package com.carmod.service.impl;

import com.carmod.exception.ResourceNotFoundException;
import com.carmod.model.Shop;
import com.carmod.repository.ShopRepository;
import com.carmod.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;

    @Override
    public Page<Shop> findAll(Pageable pageable) {
        return shopRepository.findAll(pageable);
    }

    @Override
    public Shop findById(Long id) {
        return shopRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shop", "id", id));
    }

    @Override
    public Shop save(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public Shop update(Long id, Shop shop) {
        Shop existingShop = findById(id);
        existingShop.setName(shop.getName());
        existingShop.setDescription(shop.getDescription());
        existingShop.setAddress(shop.getAddress());
        existingShop.setPhoneNumber(shop.getPhoneNumber());
        existingShop.setServices(shop.getServices());
        existingShop.setBrands(shop.getBrands());
        existingShop.setRegionCode(shop.getRegionCode());
        existingShop.setLatitude(shop.getLatitude());
        existingShop.setLongitude(shop.getLongitude());
        return shopRepository.save(existingShop);
    }

    @Override
    public void delete(Long id) {
        Shop shopToDelete = findById(id);
        shopRepository.delete(shopToDelete);
    }

    @Override
    public Page<Shop> findByRegion(String regionCode, Pageable pageable) {
        return shopRepository.findByRegionCode(regionCode, pageable);
    }

    @Override
    public Page<Shop> findByBrand(String brand, Pageable pageable) {
        return shopRepository.findByBrandsContaining(brand, pageable);
    }

    @Override
    public Page<Shop> findByService(String service, Pageable pageable) {
        return shopRepository.findByServicesContaining(service, pageable);
    }
}