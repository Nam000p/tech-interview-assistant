package com.namdx.candidate.service;

import com.namdx.candidate.dto.resume.ResumeResponse;
import com.namdx.candidate.dto.resume.ResumeUpdateRequest;

import java.util.UUID;

public interface ResumeService {
    ResumeResponse uploadResume(UUID candidateId, String filePath, String extractedText);

    ResumeResponse updateResume(UUID resumeId, ResumeUpdateRequest request, String userId);

    void deleteResume(UUID resumeId, String userId);
}