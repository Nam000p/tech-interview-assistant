package com.namdx.candidate.controller;

import com.namdx.candidate.dto.CandidateRequest;
import com.namdx.candidate.dto.CandidateResponse;
import com.namdx.candidate.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/candidates")
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<CandidateResponse> create(@Valid @RequestBody CandidateRequest request) {
        CandidateResponse response = candidateService.createProfile(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponse> getById(@PathVariable UUID id) {
        CandidateResponse response = candidateService.getProfileById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CandidateResponse> getByUserId(@PathVariable UUID userId) {
        CandidateResponse response = candidateService.getProfileByUserId(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponse> update(@PathVariable UUID id, @Valid @RequestBody CandidateRequest request) {
        CandidateResponse response = candidateService.updateProfile(id, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        candidateService.deleteProfile(id);
        return new ResponseEntity<>("Candidate deleted successfully!", HttpStatus.OK);
    }

    @PostMapping("/{id}/resumes")
    public ResponseEntity<String> uploadResume(@PathVariable UUID id, @RequestParam("file") MultipartFile file) {
        String path = "/uploads/resumes/" + file.getOriginalFilename();
        String extractedText = "AI extracted content will go here...";
        candidateService.uploadResume(id, path, extractedText);
        return new ResponseEntity<>("Resume uploaded successfully: " + file.getOriginalFilename(), HttpStatus.OK);
    }
}