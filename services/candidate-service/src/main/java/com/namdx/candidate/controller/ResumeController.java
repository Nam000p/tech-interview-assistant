package com.namdx.candidate.controller;

import com.namdx.candidate.dto.resume.ResumeResponse;
import com.namdx.candidate.dto.resume.ResumeUpdateRequest;
import com.namdx.candidate.service.ResumeService;
import com.namdx.common.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public ResumeResponse uploadResume(
            @Argument String filePath,
            @Argument String extractedText,
            @AuthenticationPrincipal UserPrincipal principal) {
        return resumeService.uploadResume(UUID.fromString(principal.getId()), filePath, extractedText);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public ResumeResponse updateResume(
            @Argument UUID id,
            @Argument @Valid ResumeUpdateRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return resumeService.updateResume(id, request, principal.getId());
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public Boolean deleteResume(@Argument UUID id, @AuthenticationPrincipal UserPrincipal principal) {
        resumeService.deleteResume(id, principal.getId());
        return true;
    }
}