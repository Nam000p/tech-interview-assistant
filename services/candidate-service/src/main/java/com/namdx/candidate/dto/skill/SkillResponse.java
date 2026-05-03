package com.namdx.candidate.dto.skill;

public record SkillResponse(
        Long id,
        String skillName,
        Integer expYears
) {}