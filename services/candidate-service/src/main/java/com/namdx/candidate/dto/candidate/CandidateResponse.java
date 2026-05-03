package com.namdx.candidate.dto.candidate;

import com.namdx.candidate.dto.resume.ResumeResponse;
import com.namdx.candidate.dto.skill.SkillResponse;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record CandidateResponse (
    UUID id,
    UUID userId,
    String fullName,
    String phone,
    String currentJobTitle,
    List<ResumeResponse> resumes,
    List<SkillResponse> skills
) {}