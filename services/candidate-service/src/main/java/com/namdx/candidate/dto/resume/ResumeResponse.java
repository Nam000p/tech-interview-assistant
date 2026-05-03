package com.namdx.candidate.dto.resume;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Builder
public record ResumeResponse(
    UUID id,
    String filePath,
    String extractedText,
    Map<String, Object> parsedData,
    OffsetDateTime uploadedAt
) {}