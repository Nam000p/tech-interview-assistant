# AI-Powered Technical Interview Assistant

## Overview
A smart recruitment platform designed to optimize the technical evaluation process using AI.

## Key Features
* **JD Analysis**: Automatically identify core skills and requirements from Job Descriptions.
* **Personalized Interviewing**: Generate technical questions based on candidate CVs and JD requirements.
* **AI Scoring**: Analyze problem-solving logic and provide in-depth feedback using LLMs.
* **Efficiency**: Streamline the screening process to identify high-quality candidates.

## Tech Stack
* **Backend**: Java, Spring Boot, Spring Cloud, Spring AI.
* **API**: GraphQL (Netflix DGS).
* **Database**: PostgreSQL, Redis (Distributed Session).
* **Frontend**: Angular.

## Project Directory Structure
```Plaintext
tech-interview-assistant/ (Root)
├── infrastructure/
│   ├── eureka-server/             # Service Discovery (Week 1) 
│   ├── config-server/             # Centralized Configuration (Week 1) 
│   └── api-gateway-dgs/           # GraphQL Gateway & Netflix DGS (Week 4) 
├── services/
│   ├── identity-service/          # Auth, RBAC & Redis Session (Week 2) 
│   ├── candidate-service/         # Candidate Profiles (Week 3) 
│   ├── interview-service/         # Interview Sessions Management (Week 3) 
│   └── ai-engine-service/         # Spring AI, JD Analysis & Scoring (Week 5-6) 
├── shared-library/                # Common DTOs, Exceptions & Utilities
├── angular-client/                # Angular Frontend (Week 7) 
├── docker/                        # Docker Compose, Postgres & Redis configs (Week 1) 
└── pom.xml                        # Parent POM
```