package com.namdx.candidate.service;

import com.namdx.candidate.dto.candidate.CandidateCreateRequest;
import com.namdx.candidate.dto.candidate.CandidateResponse;
import com.namdx.candidate.dto.candidate.CandidateUpdateRequest;

import java.util.UUID;

public interface CandidateService {
    CandidateResponse createProfile(CandidateCreateRequest request, UUID uuid);

    CandidateResponse getProfileById(UUID id);

    CandidateResponse getProfileByUserId(UUID userId);

    CandidateResponse updateProfile(UUID id, CandidateUpdateRequest request);

    void deleteProfile(UUID id, String userId);
}