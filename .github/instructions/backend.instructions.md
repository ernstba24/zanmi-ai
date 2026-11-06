---
applyTo: "zanmi-backend/**"
---

# ZANMI AI Backend — Module Instructions

## Technology context
- Primary language: Java 21.
- Framework: Spring Boot.
- Build: Maven.
- API style: REST.
- Initial architecture: modular monolith with clear package boundaries.
- Root Java package: `com.zanmi.ai`.

## Module responsibilities
This module owns the primary application API, application use cases, domain model, and adapters to infrastructure such as LLM providers and persistence.

Use these conceptual boundaries when the project reaches the relevant implementation stage:
- `api`: controllers and HTTP request/response models.
- `application`: use-case entry points and orchestration interfaces.
- `domain`: domain concepts and business rules, implemented only from developer-provided requirements.
- `infrastructure`: framework/provider/database adapters.
- `config`: Spring configuration and typed application properties.

Do not create every package or class preemptively. Create only the structure needed for the approved story.

## Copilot may help with
- Maven configuration and dependency setup.
- Spring Boot application entry-point boilerplate.
- Package and class skeletons.
- Controller, DTO, interface, configuration, and exception-handler skeletons when explicitly requested.
- Spring configuration properties and environment-variable placeholders.
- Actuator and basic health endpoint configuration.
- JUnit/Spring test scaffolding.
- Explaining Spring annotations, dependency injection, auto-configuration, Maven, and project layout.

## Do not implement without developer-defined behavior
- Chat completion workflow or prompt behavior.
- Domain services, business rules, or use-case logic.
- Persistence semantics, transactions, or data lifecycle decisions.
- Authorization rules, user ownership, or permission policies.
- Retry, fallback, caching, or circuit-breaker policies.
- LLM tool calling, RAG orchestration, or agent behavior.

For a class that will later contain behavior, generate only its agreed skeleton, signatures, and TODO markers. Ask the developer to define behavior before filling it in.

## Java and Spring conventions
- Use constructor injection; do not introduce field injection.
- Prefer explicit, understandable types and focused classes.
- Keep controllers thin; do not place application behavior in controllers.
- Use configuration properties for grouped application settings when appropriate.
- Avoid static global state.
- Avoid adding Lombok or other convenience libraries unless the developer approves them.
- Do not select or upgrade Spring Boot/Spring AI versions without checking compatibility and asking.
- Keep Java code compatible with Java 21.

## Tests
- Generate test class and fixture scaffolding when requested.
- Explain the distinction between unit, slice, and integration tests.
- Do not invent expected business outcomes. Ask the developer to define expected behavior before writing behavior-focused assertions.
- Never claim tests pass unless they were actually executed and the output confirms success.

## Approval boundary
Before modifying files or dependencies, explain the exact files and changes and ask for approval. After implementing one approved step, explain the code and stop for the next instruction.
