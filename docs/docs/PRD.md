# ZANMI AI — Product Requirements Document

**Repository:** `zanmi-ai`  
**Product:** ZANMI AI  
**Tagline:** Your Personal Intelligence Companion  
**Status:** Initial product baseline  
**Version:** 1.0

## 1. Product vision

Build an intelligent personal assistant that centralizes personal knowledge, understands software projects, supports continuous learning, and helps manage everyday tasks through large language models (LLMs), retrieval-augmented generation (RAG), Model Context Protocol (MCP) integrations, and carefully governed agentic workflows.

ZANMI AI is personal-first and extensible. Its first value is helping one person find, understand, connect, and act on their own information. Its architecture should leave room for future team or organizational use without designing an enterprise platform prematurely.

## 2. Problem statement

Useful information is scattered across documents, notes, repositories, learning resources, and task systems. Conventional chat assistants often lack durable context about a person's own material, cannot reliably explain where an answer came from, and have limited ability to safely work across tools.

ZANMI AI aims to provide one consistent interface for:
- Asking questions across personal knowledge with source references.
- Understanding software projects and technical decisions.
- Learning unfamiliar technologies and preparing for technical interviews.
- Organizing research, tasks, and repeatable workflows.
- Connecting external capabilities through permissioned tools and MCP.

## 3. Target users

### Primary user
An individual who wants a private, persistent AI companion for knowledge management, engineering work, learning, and productivity.

### Future users
Small teams or professional groups that need shared knowledge and controlled AI-assisted workflows. Team collaboration is a future direction, not an MVP requirement.

## 4. Product principles

- **Personal-first:** solve recurring individual needs before team administration.
- **Grounded:** distinguish retrieved facts from model-generated inference and cite sources where applicable.
- **User-controlled:** the user decides what content is added, connected, retained, or deleted.
- **Progressive autonomy:** begin with question-answering; add tool execution and agents only with explicit permissions and approval boundaries.
- **Provider-aware:** avoid unnecessary lock-in to a single model provider.
- **Observable:** capture useful operational metadata without logging secrets or unnecessary personal content.
- **Incremental engineering:** modular monolith first; split services only when justified.

## 5. Product scope

### Domain A — Zanmi Knowledge
- Upload and manage supported documents (initially PDF, TXT, and Markdown).
- Store document metadata and processing status.
- Search and retrieve relevant passages.
- Ask natural-language questions across selected knowledge.
- Generate summaries and explanations grounded in retrieved content.
- Display source references for generated answers.
- Delete documents and associated derived data.

### Domain B — Zanmi Engineering
- Connect selected personal Git repositories in a later release.
- Explain repository structure, components, dependencies, and data flows.
- Help investigate code and technical errors.
- Summarize project documentation and architecture decisions.
- Keep repository access scoped and read-only initially.

### Domain C — Zanmi Learning
- Explain technical concepts at adjustable depth.
- Create learning plans, study guides, quizzes, and interview practice.
- Use the user's approved notes and project context to personalize explanations.
- Track learning topics and progress in a later increment.

### Domain D — Zanmi Productivity
- Capture tasks, notes, and follow-ups.
- Summarize research and organize findings.
- Support repeatable workflows.
- Introduce external actions only through explicit permissions and human approval.

### Platform capabilities
- Conversation history and context management.
- Model-provider configuration.
- Authentication and authorization as the application expands.
- Auditability for integrations and tool calls.
- Evaluation of answer quality, retrieval quality, latency, and cost.

## 6. Release 1 / MVP scope

**MVP objective:** establish a working backend foundation and deliver a basic chat experience that can send a user prompt to an LLM and return a response reliably.

MVP includes:
- Java/Spring Boot backend.
- Health endpoint.
- `POST /api/chat`.
- Initial OpenAI model integration through Spring AI.
- Externalized configuration for API credentials and model settings.
- Request validation and consistent error responses.
- Automated unit and basic integration tests.
- README with local setup instructions.

MVP explicitly excludes:
- RAG, embeddings, vector database, and document ingestion.
- Python services and LangGraph.
- MCP and A2A.
- React frontend.
- PostgreSQL persistence and conversation history.
- Keycloak or production identity integration.
- Docker/Kubernetes deployment.
These are introduced in later releases after the baseline is stable.

## 7. Functional requirements

| ID | Requirement | Priority |
|---|---|---|
| FR-001 | The system shall expose a health endpoint. | Must |
| FR-002 | The system shall accept a chat prompt through a REST API. | Must |
| FR-003 | The system shall call the configured LLM provider and return its response. | Must |
| FR-004 | Provider credentials shall be externalized and excluded from source control. | Must |
| FR-005 | The API shall validate required request fields and reject invalid input. | Must |
| FR-006 | The API shall return consistent errors for provider failures and invalid requests. | Must |
| FR-007 | The system shall support document ingestion and metadata management in the RAG release. | Later |
| FR-008 | The system shall retrieve relevant document passages and cite sources in answers. | Later |
| FR-009 | The system shall support authorized repository analysis. | Later |
| FR-010 | The system shall expose approved external tools through MCP integrations. | Later |
| FR-011 | The system shall require approval for consequential external actions. | Later |

## 8. Non-functional requirements

- **Security:** secrets externalized; least privilege; no arbitrary shell or SQL execution exposed to the model.
- **Privacy:** do not ingest personal content without user intent; provide clear deletion and retention behavior as persistence is added.
- **Reliability:** predictable error handling, timeouts, and bounded retries where appropriate.
- **Maintainability:** clear package boundaries, automated tests, configuration separated from code.
- **Extensibility:** model provider and integration boundaries should allow future alternatives.
- **Performance:** establish baseline latency and token/cost metrics before optimization; set measurable targets after an initial workload is observed.
- **Explainability:** cite retrieved sources and disclose when evidence is insufficient.
- **Accessibility/usability:** future UI should be keyboard accessible and communicate processing/error states clearly.

## 9. Success measures

### MVP
- Application starts reliably in a local development environment.
- Health endpoint returns a healthy status.
- Valid chat request returns a provider response.
- Invalid input returns a clear client error.
- Missing/invalid provider configuration fails safely without leaking secrets.
- Automated tests cover core request, service, and failure paths.

### Knowledge release
- Retrieval evaluation set measures Recall@K.
- Answer evaluation measures faithfulness and citation correctness.
- User can trace an answer to the source document and passage.
- Document deletion removes its searchable representation.

### Operational measures
- Request latency, provider errors, token usage, and estimated cost can be inspected.
- Tool calls are permission-checked and auditable.

## 10. Assumptions and constraints

- Initial development is for one user and local development.
- The first model provider is OpenAI, accessed through a server-side integration.
- Java/Spring Boot is the primary application backend.
- Python is introduced where it offers clear value for AI experimentation, document processing, or agent orchestration.
- Exact library versions will be selected based on compatibility at implementation time.

## 11. Risks and mitigations

| Risk | Mitigation |
|---|---|
| Hallucinated answers | RAG grounding, citations, uncertainty handling, evaluation |
| Prompt injection in documents/tools | Treat retrieved content as untrusted data; isolate instructions and tool policies |
| Secret exposure | Externalized secrets, safe logging, secret scanning |
| Unbounded agent actions | Tool allowlists, scoped permissions, approval gates, audit logs |
| Model/provider changes | Provider abstraction and contract tests |
| Scope expansion | Release gates and explicit backlog prioritization |

## 12. Initial release acceptance

The MVP is accepted when Sprint 1 stories are complete, the backend can run locally, `/actuator/health` (or the agreed health route) responds, `POST /api/chat` returns an LLM response for a valid request, failure cases are handled, tests pass, and setup documentation is usable.
