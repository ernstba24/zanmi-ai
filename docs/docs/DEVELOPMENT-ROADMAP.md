# ZANMI AI — Development Roadmap

**Repository:** `zanmi-ai`  
**Planning approach:** progressive delivery, with Java/Spring Boot as the initial foundation.

## Roadmap overview

| Release | Theme | Primary outcome |
|---|---|---|
| R1 | Assistant Foundation & Chat | Working local AI chat backend |
| R2 | Personal Knowledge Assistant | Document ingestion, RAG, grounded answers |
| R3 | Engineering & Learning Intelligence | Repository understanding and learning workflows |
| R4 | MCP Integrations | Build and consume permissioned MCP tools |
| R5 | Agentic Assistant | Stateful, bounded multi-step workflows |
| R6 | Multi-agent & Productivity Expansion | A2A collaboration and broader productivity integrations |

The sequence is intentional: first make a reliable model-backed application, then add durable knowledge, then connect tools, then introduce autonomy.

## R1 — Assistant Foundation & Chat

**Goal:** establish a maintainable Java backend and a working LLM request/response path.

Scope:
- Java 21+ and Spring Boot application.
- REST API and health endpoint.
- Spring AI integration with OpenAI as the first provider.
- Externalized model configuration and secret handling.
- Request validation, error mapping, tests, and local setup documentation.

Exit criteria:
- App runs locally.
- `GET /actuator/health` succeeds.
- `POST /api/chat` returns a model response for valid input.
- Invalid input and provider failures are handled.
- No credentials are committed or leaked in logs.

Not in R1: RAG, Python, PostgreSQL, frontend, Keycloak, MCP, agents, Docker, Kubernetes.

## R2 — Personal Knowledge Assistant

**Goal:** let the user ask questions over approved personal documents and verify answers.

Scope:
- PostgreSQL and pgvector.
- Document metadata and controlled file storage.
- Ingestion pipeline for PDF, TXT, and Markdown.
- Text extraction, chunking, embeddings, and vector retrieval.
- Source-grounded answers and citations.
- Conversation persistence where useful.
- Initial RAG evaluation set.

Exit criteria:
- User can upload, list, query, and delete documents.
- Answers cite source documents/passages.
- Retrieval and answer quality are measured.
- Authorization filters are applied before retrieval.

## R3 — Engineering & Learning Intelligence

**Goal:** make ZANMI AI useful for software project understanding and structured learning.

Scope:
- Connect selected Git repositories with read-only access.
- Index repository documentation and selected source artifacts.
- Explain architecture, dependencies, and code paths with file references.
- Technical troubleshooting workflows.
- Generate study plans, study guides, quizzes, and interview preparation material.
- Add Python utilities for parsing/evaluation when they materially improve quality.

Exit criteria:
- Repository scope and permissions are visible.
- Codebase answers reference supporting files.
- Learning artifacts can be saved and revisited.
- Sensitive repository content is protected.

## R4 — MCP Integrations

**Goal:** connect the assistant to narrowly scoped external capabilities through MCP.

Scope:
- Build a Python MCP server using the official Python SDK.
- Consume the MCP server from Java/Spring AI.
- Define typed tool schemas, permissions, timeouts, and audit events.
- Start with safe read-only tools.
- Add explicit approval gates before consequential actions.

Exit criteria:
- Client discovers only configured tools.
- Tool calls are validated, authorized, and observable.
- Tool errors/timeouts are handled.
- Approval is required for consequential actions.

## R5 — Agentic Assistant

**Goal:** support bounded multi-step tasks rather than only single-turn chat and tool calls.

Scope:
- Introduce LangGraph for stateful workflows where appropriate.
- Define agent state, goals, allowed tools, iteration limits, and stop conditions.
- Add pause/resume and human approval checkpoints.
- Display workflow progress and tool activity.
- Evaluate completion quality, safety, latency, and cost.

Exit criteria:
- At least one end-to-end workflow reliably completes a defined task.
- Workflow execution is bounded and auditable.
- User can intervene, approve, reject, or stop.

## R6 — Multi-agent & Productivity Expansion

**Goal:** expand from a single assistant into a coordinated personal assistant ecosystem.

Scope:
- Specialized agents for knowledge, engineering, learning, and productivity.
- A2A-style agent discovery/delegation after single-agent workflows are stable.
- Task and follow-up management.
- Additional integrations selected by user value.
- Optional multi-user/team foundations only when there is a real use case.

Exit criteria:
- Agent responsibilities and handoff contracts are explicit.
- Delegation is permissioned and traceable.
- Cross-agent workflows have measurable outcomes.

## Suggested delivery increments

1. Sprint 1: backend foundation and first LLM integration.
2. Sprint 2: improve chat contract, observability, configuration, and test coverage.
3. Sprint 3+: begin persistence and document-ingestion design only after the chat baseline is stable.
4. Each release should have a demonstrable end-to-end capability and explicit exit criteria.

## Learning progression

| Stage | Build | Concepts practiced |
|---|---|---|
| Foundation | REST chat API | LLM APIs, prompts, Spring AI, configuration, testing |
| Knowledge | Document Q&A | embeddings, chunking, vector search, RAG, evaluation |
| Engineering | Repository assistant | code indexing, retrieval, source grounding |
| Integration | MCP server/client | tool schemas, protocol, authorization, interoperability |
| Agents | Bounded workflows | state, planning, tool calling, checkpoints |
| Collaboration | Multi-agent workflows | delegation, contracts, A2A, orchestration |

## Engineering quality gates

Every increment should include:
- A working vertical slice.
- Automated tests for core and failure paths.
- Updated documentation and API examples.
- Secret and authorization review.
- Basic observability.
- A short demo or reproducible verification procedure.
- A decision record for significant architectural changes.
