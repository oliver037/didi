package com.carmod.controller;

import com.carmod.model.Case;
import com.carmod.model.User;
import com.carmod.repository.UserRepository;
import com.carmod.service.CaseService;
import com.carmod.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/cases")
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<Page<Case>> getAllCases(Pageable pageable) {
        return ResponseEntity.ok(caseService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Case> getCaseById(@PathVariable Long id) {
        return ResponseEntity.ok(caseService.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Case> createCase(@RequestBody Case caseDto) {
        return ResponseEntity.ok(caseService.save(caseDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Case> updateCase(@PathVariable Long id, @RequestBody Case caseDto) {
        return ResponseEntity.ok(caseService.update(id, caseDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCase(@PathVariable Long id) {
        caseService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/type/{modType}")
    public ResponseEntity<Page<Case>> getCasesByModType(
            @PathVariable String modType,
            Pageable pageable) {
        return ResponseEntity.ok(caseService.findByModType(modType, pageable));
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<?> uploadImages(@PathVariable String id, 
                                        @RequestParam("files") MultipartFile[] files) {
        return ResponseEntity.ok(caseService.uploadImages(id, files));
    }

    @PostMapping("/{id}/view")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> recordView(@PathVariable Long id) {
        User currentUser = userService.getCurrentUser();
        Case viewedCase = caseService.findById(id);
        currentUser.getViewedCases().add(viewedCase);
        userRepository.save(currentUser);
        return ResponseEntity.ok().build();
    }
} 