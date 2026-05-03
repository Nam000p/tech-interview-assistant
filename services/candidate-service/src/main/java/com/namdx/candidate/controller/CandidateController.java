package com.namdx.candidate.controller;

import com.namdx.candidate.dto.candidate.CandidateCreateRequest;
import com.namdx.candidate.dto.candidate.CandidateResponse;
import com.namdx.candidate.dto.candidate.CandidateUpdateRequest;
import com.namdx.candidate.service.CandidateService;
import com.namdx.common.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class CandidateController {
    private final CandidateService candidateService;

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public CandidateResponse getCandidateById(@Argument UUID id) {
        return candidateService.getProfileById(id);
    }

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    public CandidateResponse getCandidateByUserId(@AuthenticationPrincipal UserPrincipal principal) {
        return candidateService.getProfileByUserId(UUID.fromString(principal.getId()));
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public CandidateResponse createCandidate(
            @Argument @Valid CandidateCreateRequest request,
            @AuthenticationPrincipal UserPrincipal principal) {
        return candidateService.createProfile(request, UUID.fromString(principal.getId()));
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public CandidateResponse updateCandidate(@Argument UUID id, @Argument @Valid CandidateUpdateRequest request) {
        return candidateService.updateProfile(id, request);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public Boolean deleteCandidate(@Argument UUID id, @AuthenticationPrincipal UserPrincipal principal) {
        candidateService.deleteProfile(id, principal.getId());
        return true;
    }
}