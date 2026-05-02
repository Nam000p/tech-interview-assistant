package com.namdx.candidate.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CandidateResponse {
    private UUID id;
    private UUID userId;
    private String fullName;
    private String phone;
    private String currentJobTitle;
    private List<String> resumePaths;
    private List<String> skills;
}