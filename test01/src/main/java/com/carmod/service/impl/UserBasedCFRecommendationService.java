package com.carmod.service.impl;

import com.carmod.model.Case;
import com.carmod.model.User;
import com.carmod.repository.CaseRepository;
import com.carmod.repository.UserRepository;
import com.carmod.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserBasedCFRecommendationService implements RecommendationService {

    private final UserRepository userRepository;
    private final CaseRepository caseRepository;

    @Override
    public List<Case> getRecommendedCases(Long userId, int limit) {
        // 1. 找到相似用户
        List<Long> similarUsers = findSimilarUsers(userId, 10);
        
        // 2. 获取当前用户已查看的案例
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Set<Long> viewedCases = currentUser.getViewedCases().stream()
                .map(Case::getId)
                .collect(Collectors.toSet());
        
        // 3. 获取相似用户查看的案例并计算权重
        Map<Long, Double> caseScores = new HashMap<>();
        for (Long similarUserId : similarUsers) {
            double similarity = calculateSimilarity(userId, similarUserId);
            User similarUser = userRepository.findById(similarUserId).get();
            
            for (Case viewedCase : similarUser.getViewedCases()) {
                if (!viewedCases.contains(viewedCase.getId())) {
                    caseScores.merge(viewedCase.getId(), similarity, Double::sum);
                }
            }
        }
        
        // 4. 排序并返回推荐结果
        return caseScores.entrySet().stream()
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(limit)
                .map(entry -> caseRepository.findById(entry.getKey()).get())
                .collect(Collectors.toList());
    }

    @Override
    public double calculateSimilarity(Long user1Id, Long user2Id) {
        User user1 = userRepository.findById(user1Id).get();
        User user2 = userRepository.findById(user2Id).get();

        // 获取两个用户查看的案例ID集合
        Set<Long> user1Cases = user1.getViewedCases().stream()
                .map(Case::getId)
                .collect(Collectors.toSet());
        Set<Long> user2Cases = user2.getViewedCases().stream()
                .map(Case::getId)
                .collect(Collectors.toSet());

        // 计算交集大小
        Set<Long> intersection = new HashSet<>(user1Cases);
        intersection.retainAll(user2Cases);

        // 计算并集大小
        Set<Long> union = new HashSet<>(user1Cases);
        union.addAll(user2Cases);

        // 使用Jaccard相似度
        return union.isEmpty() ? 0 : (double) intersection.size() / union.size();
    }

    @Override
    public List<Long> findSimilarUsers(Long userId, int limit) {
        User currentUser = userRepository.findById(userId).get();
        
        return userRepository.findAll().stream()
                .filter(user -> !user.getId().equals(userId))
                .map(user -> new AbstractMap.SimpleEntry<>(user.getId(), calculateSimilarity(userId, user.getId())))
                .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                .limit(limit)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
} 