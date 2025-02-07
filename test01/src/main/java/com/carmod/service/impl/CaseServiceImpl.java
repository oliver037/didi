package com.carmod.service.impl;

import com.carmod.exception.ResourceNotFoundException;
import com.carmod.model.Case;
import com.carmod.repository.CaseRepository;
import com.carmod.service.CaseService;
import com.carmod.service.FileStorageService;
import com.carmod.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {
    private final CaseRepository caseRepository;
    private final UserService userService;
    private final FileStorageService fileStorageService;

    @Override
    public Page<Case> findAll(Pageable pageable) {
        return caseRepository.findAll(pageable);
    }

    @Override
    public Case findById(Long id) {
        return caseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Case", "id", id));
    }

    @Override
    public Case save(Case caseDto) {
        caseDto.setUser(userService.getCurrentUser());
        return caseRepository.save(caseDto);
    }

    @Override
    public Case update(Long id, Case caseDto) {
        Case existingCase = findById(id);
        existingCase.setTitle(caseDto.getTitle());
        existingCase.setDescription(caseDto.getDescription());
        existingCase.setCarModel(caseDto.getCarModel());
        existingCase.setModType(caseDto.getModType());
        return caseRepository.save(existingCase);
    }

    @Override
    public void delete(Long id) {
        Case caseToDelete = findById(id);
        caseRepository.delete(caseToDelete);
    }

    @Override
    public Page<Case> findByModType(String modType, Pageable pageable) {
        return caseRepository.findByModType(modType, pageable);
    }

    @Override
    public List<String> uploadImages(String id, MultipartFile[] files) {
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            String imageUrl = fileStorageService.storeFile(file);
            imageUrls.add(imageUrl);
        }
        Case caseToUpdate = findById(Long.parseLong(id));
        caseToUpdate.getImages().addAll(imageUrls);
        caseRepository.save(caseToUpdate);
        return imageUrls;
    }
} 