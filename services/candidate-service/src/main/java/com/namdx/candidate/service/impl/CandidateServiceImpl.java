package com.namdx.candidate.service.impl;

import com.namdx.candidate.dto.CandidateRequest;
import com.namdx.candidate.dto.CandidateResponse;
import com.namdx.candidate.entity.Candidate;
import com.namdx.candidate.entity.CandidateSkill;
import com.namdx.candidate.entity.Resume;
import com.namdx.candidate.repository.CandidateRepository;
import com.namdx.candidate.repository.CandidateSkillRepository;
import com.namdx.candidate.repository.ResumeRepository;
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

    private final CandidateSkillRepository skillRepository;

    private final ResumeRepository resumeRepository;

    @Override
    @Transactional
    public CandidateResponse createProfile(CandidateRequest request) {
        Candidate candidate = Candidate.builder()
                .userId(request.getUserId())
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .currentJobTitle(request.getCurrentJobTitle())
                .build();
        Candidate savedCandidate = candidateRepository.save(candidate);
        saveSkills(savedCandidate, request.getSkills());
        return mapToResponse(savedCandidate);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse getProfileById(UUID id) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found!"));
        return mapToResponse(candidate);
    }

    @Override
    @Transactional(readOnly = true)
    public CandidateResponse getProfileByUserId(UUID userId) {
        Candidate candidate = candidateRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate profile not found!"));
        return mapToResponse(candidate);
    }

    @Override
    @Transactional
    public CandidateResponse updateProfile(UUID id, CandidateRequest request) {
        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found!"));
        candidate.setFullName(request.getFullName());
        candidate.setPhone(request.getPhone());
        candidate.setCurrentJobTitle(request.getCurrentJobTitle());

        skillRepository.deleteByCandidateId(id);
        saveSkills(candidate, request.getSkills());
        return mapToResponse(candidateRepository.save(candidate));
    }

    @Override
    @Transactional
    public void deleteProfile(UUID id) {
        if (!candidateRepository.existsById(id)) {
            throw new EntityNotFoundException("Candidate not found!");
        }
        candidateRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void uploadResume(UUID candidateId, String filePath, String extractedText) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found!"));
        Resume resume = Resume.builder()
                .candidate(candidate)
                .filePath(filePath)
                .extractedText(extractedText)
                .build();
        resumeRepository.save(resume);
    }

    private void saveSkills(Candidate candidate, List<String> skillNames) {
        if (skillNames == null || skillNames.isEmpty()) {
            return;
        }
        List<CandidateSkill> skills = skillNames.stream()
                .map(name -> CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName(name)
                        .build())
                .toList();
        skillRepository.saveAll(skills);
    }

    private CandidateResponse mapToResponse(Candidate candidate) {
        CandidateResponse response = new CandidateResponse();
        response.setId(candidate.getId());
        response.setUserId(candidate.getUserId());
        response.setFullName(candidate.getFullName());
        response.setPhone(candidate.getPhone());
        response.setCurrentJobTitle(candidate.getCurrentJobTitle());
        response.setSkills(candidate.getSkills().stream()
                .map(CandidateSkill::getSkillName)
                .toList());
        return response;
    }
}