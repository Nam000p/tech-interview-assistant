package com.namdx.candidate.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "candidate_skills", schema = "candidate_schema")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CandidateSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;

    @Column(name = "skill_name", nullable = false, length = 100)
    private String skillName;

    @Column(name = "exp_years")
    private Integer expYears;
}