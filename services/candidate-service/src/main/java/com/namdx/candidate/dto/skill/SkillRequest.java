package com.namdx.candidate.dto.skill;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SkillRequest (
        @NotBlank(message = "Skill name is mandatory")
        @Size(max = 50, message = "Skill name must not exceed 50 characters")
        String skillName,

        @Min(value = 0, message = "Experience years cannot be negative")
        @Max(value = 50, message = "Experience years must be realistic (max 50)")
        Integer expYears
) {}