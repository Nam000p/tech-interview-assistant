package com.namdx.candidate.service;

import com.namdx.candidate.dto.CandidateRequest;
import com.namdx.candidate.dto.CandidateResponse;

import java.util.UUID;

public interface CandidateService {
    CandidateResponse createProfile(CandidateRequest request);

    CandidateResponse getProfileById(UUID id);

    CandidateResponse getProfileByUserId(UUID userId);

    CandidateResponse updateProfile(UUID id, CandidateRequest request);

    void deleteProfile(UUID id);

    void uploadResume(UUID candidateId, String filePath, String extractedText);
}