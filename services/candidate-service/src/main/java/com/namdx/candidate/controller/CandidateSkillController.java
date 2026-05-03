package com.namdx.candidate.controller;

import com.namdx.candidate.dto.skill.SkillRequest;
import com.namdx.candidate.dto.skill.SkillResponse;
import com.namdx.candidate.service.CandidateSkillService;
import com.namdx.common.security.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Validated
public class CandidateSkillController {
    private final CandidateSkillService skillService;

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public List<SkillResponse> addSkills(
            @Argument UUID candidateId,
            @Argument @Valid List<SkillRequest> requests,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        return skillService.addSkills(candidateId, requests, principal.getId());
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('ROLE_CANDIDATE')")
    public Boolean deleteSkill(@Argument Long id, @AuthenticationPrincipal UserPrincipal principal) {
        skillService.deleteSkill(id, principal.getId());
        return true;
    }
}