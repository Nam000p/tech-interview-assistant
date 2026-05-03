package com.namdx.candidate.service.impl;

import com.namdx.candidate.dto.skill.SkillRequest;
import com.namdx.candidate.dto.skill.SkillResponse;
import com.namdx.candidate.entity.Candidate;
import com.namdx.candidate.entity.CandidateSkill;
import com.namdx.candidate.repository.CandidateRepository;
import com.namdx.candidate.repository.CandidateSkillRepository;
import com.namdx.candidate.service.CandidateSkillService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CandidateSkillServiceImpl implements CandidateSkillService {
    private final CandidateSkillRepository skillRepository;

    private final CandidateRepository candidateRepository;

    @Override
    @Transactional
    public List<SkillResponse> addSkills(UUID candidateId, List<SkillRequest> requests, String id) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new EntityNotFoundException("Candidate not found"));

        List<CandidateSkill> skills = requests.stream()
                .map(req -> CandidateSkill.builder()
                        .candidate(candidate)
                        .skillName(req.skillName())
                        .expYears(req.expYears() != null ? req.expYears() : 0)
                        .build())
                .toList();

        List<CandidateSkill> savedSkills = skillRepository.saveAll(skills);

        return savedSkills.stream()
                .map(s -> new SkillResponse(
                        s.getId(),
                        s.getSkillName(),
                        s.getExpYears()
                ))
                .toList();
    }

    @Override
    @Transactional
    public void deleteSkill(Long skillId, String id) {
        if (!skillRepository.existsById(skillId)) {
            throw new EntityNotFoundException("Skill not found");
        }
        skillRepository.deleteById(skillId);
    }
}