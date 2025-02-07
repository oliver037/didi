package com.carmod.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ElementCollection
    @CollectionTable(name = "shop_images")
    private Set<String> images = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "shop_services")
    private Set<String> services = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "shop_brands")
    private Set<String> brands = new HashSet<>();

    @Column(name = "region_code")
    private String regionCode;

    private Double latitude;
    private Double longitude;

    @Column(name = "rating_average")
    private Double ratingAverage = 0.0;

    @Column(name = "rating_count")
    private Integer ratingCount = 0;
} 