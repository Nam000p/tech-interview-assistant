CREATE TABLE candidates (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    current_job_title VARCHAR(100)
);

CREATE TABLE resumes (
    id UUID PRIMARY KEY,
    candidate_id UUID REFERENCES candidates(id) ON DELETE CASCADE,
    file_path VARCHAR(255),
    extracted_text TEXT,
    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);