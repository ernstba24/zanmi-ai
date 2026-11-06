# ZANMI AI

**Your Personal Intelligence Companion.**

ZANMI AI is a hybrid personal AI assistant designed to centralize personal knowledge, understand software projects, support continuous learning, and help manage everyday work through LLMs, retrieval-augmented generation (RAG), MCP integrations, and agentic workflows.

> **Naming convention:** ZANMI AI is the product and user-facing brand. `zanmi-ai` is the existing GitHub repository name and is intentionally retained.

## Product domains

- **Zanmi Knowledge** — personal documents, notes, semantic search, grounded Q&A, and citations.
- **Zanmi Engineering** — codebase understanding, architecture exploration, troubleshooting, and project intelligence.
- **Zanmi Learning** — explanations, study plans, learning resources, quizzes, and interview preparation.
- **Zanmi Productivity** — tasks, research, summaries, and personal workflows.
- **Zanmi Connect** — MCP clients/servers and external integrations.
- **Zanmi Agents** — bounded agentic workflows, approvals, and eventually A2A collaboration.

## Repository identity

| Item | Value |
|---|---|
| Product | ZANMI AI |
| Tagline | Your Personal Intelligence Companion |
| GitHub repository | `zanmi-ai` |
| Java backend | `zanmi-backend` |
| Python AI services | `zanmi-ai-services` |
| MCP servers | `zanmi-mcp-servers` |
| Frontend | `zanmi-frontend` |

The `orbit-*` directory names are retained to align with the existing repository identity. Product-facing names, documentation, UI labels, and API descriptions should use ZANMI AI / Zanmi.

## Product direction

Build a **personal-first, extensible hybrid assistant**. It should be useful to an individual from the beginning while using clean boundaries and integration patterns that could later support team or organizational use. Start with a modular monolith rather than premature microservices.

## Technology direction

- Java 21+ / Spring Boot for the primary application backend.
- Spring AI for model integration, embeddings, RAG, and MCP client capabilities.
- OpenAI API as the initial model provider, behind a provider abstraction where practical.
- Python for document-processing experiments, evaluation, and later advanced agent workflows.
- PostgreSQL with pgvector for durable application data and vector search when the RAG release begins.
- React + TypeScript for the web client when backend workflows are stable.
- Python MCP SDK for initial MCP server development; Java/Spring AI MCP client for consumption.
- LangGraph for advanced stateful workflows when the agent release begins.
- Keycloak, Docker Compose, and Kubernetes/AKS introduced progressively, not in Sprint 1.

Check current compatibility matrices before selecting exact framework/library versions.

## Repository map

```text
zanmi-ai/
├── README.md
├── docs/
│   ├── PRD.md
│   ├── ARCHITECTURE.md
│   ├── USER-STORIES.md
│   ├── DEVELOPMENT-ROADMAP.md
│   ├── SPRINT-01-BACKLOG.md
│   ├── adr/
│   └── api/
├── zanmi-backend/
│   ├── pom.xml
│   └── src/
├── zanmi-ai-services/
│   ├── README.md
│   └── src/
├── zanmi-mcp-servers/
│   ├── README.md
│   └── servers/
├── zanmi-frontend/
│   ├── README.md
│   └── src/
└── infrastructure/
    ├── docker/
    ├── kubernetes/
    └── scripts/
```

See [ARCHITECTURE.md](docs/ARCHITECTURE.md) for the expanded structure and component responsibilities.

## Getting started

Sprint 1 establishes the Java/Spring Boot backend and a first protected-by-configuration LLM call. The initial implementation deliberately avoids adding RAG, Python services, PostgreSQL, MCP, A2A, frontend, Keycloak, Docker, or Kubernetes before their requirements are reached.

1. Install a supported JDK and Maven.
2. Configure the model API key outside source control.
3. Build and run `zanmi-backend`.
4. Verify the health endpoint and `POST /api/chat`.

Detailed setup instructions will be added with the Sprint 1 implementation. Never commit API keys, tokens, or personal documents.

## Documentation

- [Product Requirements Document](docs/PRD.md)
- [Architecture](docs/ARCHITECTURE.md)
- [User Stories](docs/USER-STORIES.md)
- [Development Roadmap](docs/DEVELOPMENT-ROADMAP.md)
- [Sprint 1 Backlog](docs/SPRINT-01-BACKLOG.md)

## Guiding principles

1. Personal utility before enterprise complexity.
2. Ground answers in the user's sources and clearly identify uncertainty.
3. Keep user data private, authorized, and under user control.
4. Treat retrieved content and tool outputs as untrusted input.
5. Require human approval for consequential external actions.
6. Measure quality, latency, and cost—not just whether a demo works.
7. Introduce advanced AI patterns only when a real use case needs them.
