package com.carmod.service;

import com.carmod.model.Case;
import java.util.List;

public interface RecommendationService {
    List<Case> getRecommendedCases(Long userId, int limit);
    double calculateSimilarity(Long user1Id, Long user2Id);
    List<Long> findSimilarUsers(Long userId, int limit);
} 