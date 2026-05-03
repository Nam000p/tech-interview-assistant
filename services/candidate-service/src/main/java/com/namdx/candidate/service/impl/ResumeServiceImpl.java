package com.namdx.candidate.service.impl;

import com.namdx.candidate.dto.resume.ResumeResponse;
import com.namdx.candidate.dto.resume.ResumeUpdateRequest;
import com.namdx.candidate.entity.Candidate;
import com.namdx.candidate.entity.Resume;
import com.namdx.candidate.repository.CandidateRepository;
import com.namdx.candidate.repository.ResumeRepository;
import com.namdx.candidate.service.ResumeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    private final CandidateRepository candidateRepository;

    @Override
    @Transactional
    public ResumeResponse uploadResume(UUID candidateId, String filePath, String extractedText) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found!"));

        Resume resume = Resume.builder()
                .candidate(candidate)
                .filePath(filePath)
                .extractedText(extractedText)
                .build();

        return mapToResponse(resumeRepository.save(resume));
    }

    @Override
    @Transactional
    public ResumeResponse updateResume(UUID resumeId, ResumeUpdateRequest request, String id) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new EntityNotFoundException("Resume not found!"));
        resume.setExtractedText(request.extractedText());
        resume.setParsedData(request.parsedData());
        return mapToResponse(resumeRepository.save(resume));
    }

    @Override
    @Transactional
    public void deleteResume(UUID resumeId, String id) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new EntityNotFoundException("Entity not found!");
        }
        resumeRepository.deleteById(resumeId);
    }

    private ResumeResponse mapToResponse(Resume resume) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .filePath(resume.getFilePath())
                .extractedText(resume.getExtractedText())
                .parsedData(resume.getParsedData())
                .uploadedAt(resume.getUploadedAt())
                .build();
    }
}
