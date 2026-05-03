CREATE TYPE interview_status AS ENUM (
    'PENDING',
    'IN_PROGRESS',
    'COMPLETED',
    'CANCELLED'
);

CREATE TABLE job_descriptions (
    id UUID PRIMARY KEY,
    created_by UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    core_skills TEXT[],
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE interview_sessions (
    id UUID PRIMARY KEY,
    candidate_id UUID NOT NULL,
    jd_id UUID NOT NULL REFERENCES job_descriptions(id),
    recruiter_id UUID NOT NULL,
    status interview_status DEFAULT 'PENDING',
    scheduled_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMPTZ,
    updated_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE interview_questions (
    id UUID PRIMARY KEY,
    session_id UUID NOT NULL REFERENCES interview_sessions(id) ON DELETE CASCADE,
    question_text TEXT NOT NULL,
    expected_answer TEXT,
    ai_generated BOOLEAN DEFAULT TRUE,
    time_limit INTEGER DEFAULT 120,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE interview_evaluations (
    id UUID PRIMARY KEY,
    question_id UUID NOT NULL UNIQUE REFERENCES interview_questions(id) ON DELETE CASCADE,
    candidate_answer TEXT,
    score INTEGER CHECK (score >= 0 AND score <= 100),
    score_details JSONB,
    ai_feedback TEXT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_sessions_candidate ON interview_sessions(candidate_id);
CREATE INDEX idx_sessions_recruiter ON interview_sessions(recruiter_id);
CREATE INDEX idx_questions_session ON interview_questions(session_id);