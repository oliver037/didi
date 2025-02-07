package com.carmod.repository;

import com.carmod.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CaseRepository extends JpaRepository<Case, Long> {
    Page<Case> findByModType(String modType, Pageable pageable);
    Page<Case> findByUserId(Long userId, Pageable pageable);
} 