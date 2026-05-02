CREATE TABLE interview_schema.job_descriptions (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    core_skills TEXT[]
);

CREATE TABLE interview_schema.interview_sessions (
    id UUID PRIMARY KEY,
    candidate_id UUID NOT NULL,
    jd_id UUID NOT NULL REFERENCES interview_schema.job_descriptions(id),
    recruiter_id UUID NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE interview_schema.interview_questions (
    id UUID PRIMARY KEY,
    session_id UUID NOT NULL REFERENCES interview_schema.interview_sessions(id) ON DELETE CASCADE,
    question_text TEXT NOT NULL,
    expected_answer TEXT,
    ai_generated BOOLEAN DEFAULT TRUE
);

CREATE TABLE interview_schema.interview_evaluations (
    id UUID PRIMARY KEY,
    question_id UUID NOT NULL UNIQUE REFERENCES interview_schema.interview_questions(id) ON DELETE CASCADE,
    candidate_answer TEXT,
    score INTEGER CHECK (score >= 0 AND score <= 10),
    ai_feedback TEXT
);