package com.namdx.candidate.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CandidateRequest {
    @NotNull(message = "User ID cannot be null!")
    private UUID userId;

    @NotBlank(message = "Full name is mandatory!")
    @Size(max = 100, message = "Full name must not exceed 100 characters!")
    private String fullName;

    @Size(max = 20)
    private String phone;

    private String currentJobTitle;
    private List<String> skills;
}