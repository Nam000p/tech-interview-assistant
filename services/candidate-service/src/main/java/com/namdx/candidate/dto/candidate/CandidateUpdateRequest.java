package com.namdx.candidate.dto.candidate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CandidateUpdateRequest (
    @NotBlank(message = "Full name is mandatory!")
    @Size(max = 100, message = "Full name must not exceed 100 characters!")
    String fullName,

    @Pattern(regexp = "^\\+?[0-9\\s\\-\\(\\)]{7,20}$", message = "Invalid phone number format")
    @Size(max = 20, message = "Phone must not exceed 20 characters")
    String phone,

    @Size(max = 100, message = "Current job must not exceed 100 characters")
    String currentJob
){}