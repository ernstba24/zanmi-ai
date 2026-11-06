# ZANMI AI — Sprint 1 Backlog

**Repository:** `zanmi-ai`  
**Sprint theme:** Backend Foundation & First LLM Integration  
**Sprint goal:** Deliver a runnable Java/Spring Boot backend with a health endpoint and a working `POST /api/chat` integration, including secure configuration, validation, error handling, and automated tests.

## Sprint boundaries

### In scope
- Java 21+ / Spring Boot baseline.
- Maven build.
- Clear package structure.
- Health endpoint.
- Spring AI integration with OpenAI as initial provider.
- Externalized API key/model configuration.
- Chat request/response contract.
- Validation and controlled provider error handling.
- Unit and web/integration tests.
- README setup instructions.

### Explicitly out of scope
- RAG, embeddings, document ingestion, vector database.
- Python services, LangGraph, MCP, A2A.
- React frontend.
- PostgreSQL and conversation persistence.
- Keycloak.
- Docker, Kubernetes, AKS.
- External tools or autonomous actions.

## Definition of done

- Code is committed to the `zanmi-ai` repository.
- Application builds successfully.
- Automated tests pass.
- No secrets are committed or logged.
- Health and chat endpoints are documented.
- Error responses are consistent and do not expose internal details.
- README includes prerequisites, configuration, and run/test commands.
- Sprint stories meet their acceptance criteria.

## Backlog

### US-001 — Create the backend application
**Priority:** Must  
**Estimate:** 3 points

Tasks:
- Create Spring Boot Maven project.
- Add required web and actuator dependencies.
- Establish root package `com.zanmi.ai`.
- Add application entry point.
- Configure application name and health endpoint.
- Add `.gitignore` and initial README.

Acceptance criteria:
- `mvn test` completes successfully.
- Application starts locally.
- `GET /actuator/health` returns healthy status.
- Package layout is established.

### US-002 — Establish testing and code-quality foundation
**Priority:** Must  
**Estimate:** 3 points

Tasks:
- Configure JUnit 5 and Spring Boot test dependencies.
- Add application context or controller test.
- Add service unit-test conventions.
- Ensure Maven test phase runs tests.

Acceptance criteria:
- Tests execute as part of the normal build.
- At least one successful application/web test exists.
- A failing test causes the build to fail.

### US-003 — Submit a prompt to an LLM
**Priority:** Must  
**Estimate:** 5 points

Tasks:
- Define chat request and response DTOs.
- Implement `POST /api/chat`.
- Add application-level chat service/use case.
- Integrate Spring AI chat client/model.
- Return generated text in a stable response shape.
- Add service/controller tests using mocks where appropriate.

Acceptance criteria:
- Valid request with a message returns a model response.
- Empty message is rejected.
- Controller does not contain provider-specific orchestration logic.
- Provider integration is isolated behind a service/adapter boundary.

### US-004 — Externalize model configuration
**Priority:** Must  
**Estimate:** 2 points

Tasks:
- Configure provider API key through environment/configuration.
- Configure model name and relevant generation settings externally.
- Add placeholder-only `.env.example` or equivalent configuration guide.
- Ensure secret files are ignored by Git.

Acceptance criteria:
- No real API key is committed.
- Application reads provider configuration externally.
- Missing/invalid configuration fails safely.
- Secret values are not included in logs or API responses.

### US-005 — Handle invalid requests and provider failures
**Priority:** Must  
**Estimate:** 3 points

Tasks:
- Add request validation.
- Add centralized exception handling.
- Define consistent error response structure.
- Map provider timeout/unavailability to controlled server errors.
- Add tests for validation and provider failure paths.

Acceptance criteria:
- Invalid request returns appropriate 4xx status.
- Provider failure returns controlled 5xx response.
- Response does not expose stack traces, credentials, or internal implementation details.
- Failure scenarios are covered by tests.

## Suggested implementation sequence

| Order | Story | Dependency |
|---|---|---|
| 1 | US-001 — Backend application | None |
| 2 | US-002 — Test foundation | US-001 |
| 3 | US-004 — Externalized configuration | US-001 |
| 4 | US-003 — LLM chat endpoint | US-001, US-004 |
| 5 | US-005 — Validation and failures | US-003 |

## Proposed API contract

### `POST /api/chat`

Request:
```json
{
  "message": "Explain the purpose of dependency injection in Spring."
}
```

Success response:
```json
{
  "response": "Dependency injection is..."
}
```

Example validation error:
```json
{
  "code": "VALIDATION_ERROR",
  "message": "message must not be blank"
}
```

The exact error schema can be finalized during implementation, but it must remain consistent and must not expose internal details.

## Sprint review demonstration

1. Start the application locally.
2. Call `GET /actuator/health`.
3. Submit a valid message to `POST /api/chat`.
4. Demonstrate rejection of an empty message.
5. Demonstrate controlled behavior when provider configuration is missing or the provider fails.
6. Run the automated test suite.
7. Show that secrets are externalized and absent from source control.

## Sprint retrospective prompts

- Was the Spring AI integration straightforward to test?
- Are API and application responsibilities clearly separated?
- Are configuration and secret-handling instructions easy to follow?
- Which errors were difficult to diagnose?
- What should be improved before adding persistence and RAG?
