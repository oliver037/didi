package com.carmod.controller;

import com.carmod.model.Case;
import com.carmod.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping("/cases")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Case>> getRecommendedCases(
            @RequestParam(defaultValue = "10") int limit) {
        // 从SecurityContext获取当前用户ID
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(recommendationService.getRecommendedCases(userId, limit));
    }

    private Long getCurrentUserId() {
        // 实现获取当前用户ID的逻辑
        return 1L; // 临时返回，需要实现实际逻辑
    }
} 