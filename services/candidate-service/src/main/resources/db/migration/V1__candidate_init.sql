CREATE TABLE candidate_schema.candidates (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    current_job_title VARCHAR(100)
);

CREATE TABLE candidate_schema.resumes (
    id UUID PRIMARY KEY,
    candidate_id UUID NOT NULL REFERENCES candidate_schema.candidates(id) ON DELETE CASCADE,
    file_path VARCHAR(255),
    extracted_text TEXT,
    uploaded_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE candidate_schema.candidate_skills (
    id SERIAL PRIMARY KEY,
    candidate_id UUID NOT NULL REFERENCES candidate_schema.candidates(id) ON DELETE CASCADE,
    skill_name VARCHAR(100) NOT NULL,
    exp_years INTEGER
);