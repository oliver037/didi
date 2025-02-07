package com.carmod.service;

import com.carmod.model.Case;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CaseService {
    Page<Case> findAll(Pageable pageable);
    Case findById(Long id);
    Case save(Case caseDto);
    Case update(Long id, Case caseDto);
    void delete(Long id);
    Page<Case> findByModType(String modType, Pageable pageable);
    List<String> uploadImages(String id, MultipartFile[] files);
} 