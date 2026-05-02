package com.namdx.candidate.repository;

import com.namdx.candidate.entity.CandidateSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, Long> {
    void deleteByCandidateId(UUID candidateId);
}