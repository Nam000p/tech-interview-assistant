package com.namdx.candidate.service;

import com.namdx.candidate.dto.skill.SkillRequest;
import com.namdx.candidate.dto.skill.SkillResponse;

import java.util.List;
import java.util.UUID;

public interface CandidateSkillService {
    List<SkillResponse> addSkills(UUID candidateId, List<SkillRequest> requests, String userId);

    void deleteSkill(Long skillId, String userId);
}