package com.namdx.candidate.service.impl;

import com.namdx.candidate.dto.candidate.CandidateCreateRequest;
import com.namdx.candidate.dto.candidate.CandidateResponse;
import com.namdx.candidate.dto.candidate.CandidateUpdateRequest;
import com.namdx.candidate.dto.resume.ResumeResponse;
import com.namdx.candidate.dto.skill.SkillResponse;
import com.namdx.candidate.entity.Candidate;
import com.namdx.candidate.repository.CandidateRepository;
import com.namdx.candidate.service.CandidateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;

    @Override
    @Transactional
    public CandidateResponse createProfile(CandidateCreateRequest request, UUID uuid) {
        if (candidateRepository.findByUserId(uuid).isPresent()) {
            throw new IllegalStateException("Candidate profile already exists for this user!");
        }

        Candidate candidate = Candidate.builder()
                .userId(uuid)
                .fullName(request.fullName())
                .phone(request.phone())
                .currentJob(request.currentJob())
                .build();

        return mapToResponse(candidateRepository.save(candidate));
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse getProfileById(UUID id) {
        return candidateRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found!"));
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse getProfileByUserId(UUID userId) {
        return candidateRepository.findByUserId(userId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new EntityNotFoundException("Candidate profile not found!"));
    }

    @Override
    @Transactional
    public CandidateResponse updateProfile(UUID id, CandidateUpdateRequest request) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found!"));

        candidate.setFullName(request.fullName());
        candidate.setPhone(request.phone());
        candidate.setCurrentJob(request.currentJob());

        return mapToResponse(candidateRepository.save(candidate));
    }

    @Override
    @Transactional
    public void deleteProfile(UUID id, String principalId) {
        if (!candidateRepository.existsById(id)) {
            throw new EntityNotFoundException("Candidate not found!");
        }
        candidateRepository.deleteById(id);
    }

    private CandidateResponse mapToResponse(Candidate candidate) {
        List<ResumeResponse> resumeResponses = (candidate.getResumes() == null) ? List.of() :
                candidate.getResumes().stream()
                        .map(r -> ResumeResponse.builder()
                                .id(r.getId())
                                .filePath(r.getFilePath())
                                .extractedText(r.getExtractedText())
                                .parsedData(r.getParsedData())
                                .uploadedAt(r.getUploadedAt())
                                .build())
                        .toList();

        List<SkillResponse> skillResponses = (candidate.getSkills() == null) ? List.of() :
                candidate.getSkills().stream()
                        .map(s -> new SkillResponse(
                                s.getId(),
                                s.getSkillName(),
                                s.getExpYears()
                        ))
                        .toList();

        return CandidateResponse.builder()
                .id(candidate.getId())
                .userId(candidate.getUserId())
                .fullName(candidate.getFullName())
                .phone(candidate.getPhone())
                .currentJobTitle(candidate.getCurrentJob())
                .resumes(resumeResponses)
                .skills(skillResponses)
                .build();
    }
}