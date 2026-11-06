# ZANMI AI — Epics & User Stories

**Repository:** `zanmi-ai`  
**Product:** ZANMI AI

Stories are grouped by product capability and sequenced by release. Priorities are initial planning guidance and may change after implementation feedback.

## Epic 1 — Assistant foundation and chat

### US-001 — Create the backend application
**As a** developer, **I want** a Spring Boot backend with a clear package structure, **so that** ZANMI AI has a maintainable API foundation.

Acceptance criteria:
- Application builds and starts locally.
- Health endpoint returns a healthy status.
- Package structure separates API, application, domain, infrastructure, and configuration concerns.
- README documents prerequisites and run instructions.

### US-002 — Establish testing and code-quality foundation
**As a** developer, **I want** automated tests and consistent project conventions, **so that** changes can be made safely.

Acceptance criteria:
- Unit test framework is configured.
- At least one application/context or web-layer test runs.
- Build fails when tests fail.
- Formatting and basic code conventions are documented or automated.

### US-003 — Submit a prompt to an LLM
**As a** user, **I want** to submit a message and receive an AI response, **so that** I can use ZANMI AI as a conversational assistant.

Acceptance criteria:
- `POST /api/chat` accepts a non-empty message.
- Valid request is sent to the configured model provider.
- Successful response contains generated text.
- Provider integration is isolated behind an application/infrastructure boundary.

### US-004 — Externalize model configuration
**As a** developer, **I want** model credentials and settings outside source code, **so that** secrets are not committed and environments can be configured safely.

Acceptance criteria:
- API key is supplied through external configuration.
- No real secret appears in source control or example files.
- Missing configuration produces a clear startup or request failure.
- Logs do not expose secret values.

### US-005 — Handle invalid requests and provider failures
**As a** user, **I want** clear and consistent error responses, **so that** I understand when a request cannot be completed.

Acceptance criteria:
- Empty or malformed requests return an appropriate 4xx response.
- Provider timeout/unavailability is mapped to a controlled error response.
- Internal exception details and secrets are not returned to clients.
- Failure paths have automated tests.

## Epic 2 — Personal knowledge and RAG

### US-006 — Upload a knowledge document
**As a** user, **I want** to upload supported documents, **so that** ZANMI AI can answer questions using my own material.

Acceptance criteria:
- Supported formats and size limits are validated.
- Document metadata and processing status are recorded.
- Unsupported or corrupt files fail with clear feedback.
- Document content is not exposed across user authorization boundaries.

### US-007 — Search personal knowledge
**As a** user, **I want** to search my documents using natural language, **so that** I can find relevant information without knowing exact keywords.

Acceptance criteria:
- Query retrieves relevant authorized passages.
- Results identify their source document.
- Retrieval is evaluated against a small curated dataset.

### US-008 — Ask questions with source citations
**As a** user, **I want** answers grounded in my documents with citations, **so that** I can verify the response.

Acceptance criteria:
- Relevant passages are included in model context.
- Answer includes source references.
- System indicates when retrieved evidence is insufficient.
- Citation correctness is tested.

### US-009 — Manage and delete documents
**As a** user, **I want** to view and delete my documents, **so that** I control my knowledge collection.

Acceptance criteria:
- User can list documents and processing states.
- User can delete a document.
- Deletion removes associated chunks and embeddings according to retention policy.

## Epic 3 — Engineering intelligence

### US-010 — Connect a Git repository
**As a** user, **I want** to connect a repository with limited permissions, **so that** ZANMI AI can understand my software projects.

Acceptance criteria:
- Repository access is explicitly authorized.
- Initial access is read-only.
- Repository scope and sync status are visible.
- Secrets and private source code are handled according to configured retention controls.

### US-011 — Explain project architecture
**As a** developer, **I want** to ask questions about a connected codebase, **so that** I can understand components, dependencies, and data flows.

Acceptance criteria:
- Answers reference relevant files and symbols where possible.
- The assistant distinguishes direct code evidence from inference.
- Access is limited to repositories the user authorized.

### US-012 — Investigate a technical issue
**As a** developer, **I want** help correlating logs, code, and documentation, **so that** I can accelerate troubleshooting and root-cause analysis.

Acceptance criteria:
- User can provide an error or symptom.
- Assistant identifies supporting evidence and possible hypotheses.
- Recommendations distinguish verified facts from suggestions.
- No code or infrastructure change is executed without approval.

## Epic 4 — Learning intelligence

### US-013 — Generate a learning plan
**As a** learner, **I want** a learning plan based on a goal and current knowledge, **so that** I can study progressively.

Acceptance criteria:
- Plan is divided into ordered topics and milestones.
- User can adjust depth and pace.
- Plan can be saved for later reference.

### US-014 — Generate study material and quizzes
**As a** learner, **I want** explanations, study guides, and quizzes, **so that** I can reinforce technical concepts.

Acceptance criteria:
- User can specify topic and explanation depth.
- Quiz questions include answers and explanations.
- When personal sources are used, relevant sources are cited.

### US-015 — Track learning progress
**As a** learner, **I want** to track topics and completed activities, **so that** I can see progress over time.

Acceptance criteria:
- User can mark topics or activities complete.
- Progress is persisted.
- User can review completed and remaining topics.

## Epic 5 — MCP integrations

### US-016 — Build a purpose-built MCP server
**As a** developer, **I want** to expose a small set of approved capabilities through an MCP server, **so that** ZANMI AI can use external tools through a standard protocol.

Acceptance criteria:
- Server exposes documented tools with typed input schemas.
- Tools enforce scope and validation.
- Errors are structured and do not leak secrets.
- Tool behavior is covered by tests.

### US-017 — Consume an MCP server from Java
**As a** developer, **I want** the Spring Boot application to consume an MCP server, **so that** the assistant can discover and invoke approved tools.

Acceptance criteria:
- MCP client connects using a documented transport.
- Only configured tools are available.
- Tool invocation is permission-checked and logged.
- Failure and timeout behavior is tested.

### US-018 — Approve sensitive tool actions
**As a** user, **I want** to approve consequential tool actions, **so that** the assistant cannot take important actions without my consent.

Acceptance criteria:
- Tools declare whether an action is read-only or consequential.
- Consequential actions pause for approval.
- Approval/denial is recorded.
- Denied actions are not executed.

## Epic 6 — Agentic workflows and collaboration

### US-019 — Run a bounded multi-step workflow
**As a** user, **I want** an agent to complete a defined multi-step task, **so that** I can delegate repeatable work while retaining control.

Acceptance criteria:
- Workflow has explicit goal, allowed tools, limits, and stop conditions.
- Progress and tool calls are visible.
- Workflow can pause for user input or approval.
- Maximum iterations/time/cost are bounded.

### US-020 — Delegate work between specialized agents
**As a** user, **I want** specialized agents to collaborate on a task, **so that** complex work can be divided into focused responsibilities.

Acceptance criteria:
- Agent roles and handoff contracts are documented.
- Delegation scope is constrained.
- Handoffs and outputs are traceable.
- A2A communication is introduced only after single-agent workflows are reliable.

## Epic 7 — Personal productivity

### US-021 — Capture and organize tasks
**As a** user, **I want** to capture tasks and follow-ups, **so that** ZANMI AI can help me organize personal work.

Acceptance criteria:
- User can create, update, complete, and list tasks.
- Tasks can include due dates and notes.
- Assistant can summarize tasks without changing them unexpectedly.

### US-022 — Research and summarize information
**As a** user, **I want** the assistant to organize research into concise findings, **so that** I can make progress without manually consolidating every source.

Acceptance criteria:
- Research output distinguishes sourced facts from synthesis.
- Sources are retained where available.
- User can save findings to personal knowledge.

## Cross-cutting requirements
- Authorization is enforced server-side.
- User content and tool output are treated as untrusted.
- Secrets are never exposed to the model or client unnecessarily.
- Consequential actions require explicit approval.
- Core flows have automated tests and observable failures.
