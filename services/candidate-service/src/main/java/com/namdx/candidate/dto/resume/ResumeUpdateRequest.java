package com.namdx.candidate.dto.resume;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.Map;

public record ResumeUpdateRequest(
   @NotBlank(message = "Extracted text cannot be blank")
   @Size(max = 100000, message = "Extracted text is too large (max 100,000 characters)")
   String extractedText,

   @NotEmpty(message = "Parsed data cannot be null or empty")
   Map<String, Object> parsedData
) {}