---
applyTo: "zanmi-ai-services/**"
---

# ZANMI AI Python AI Services — Module Instructions

## Technology context
- Language: Python 3.x.
- This module is reserved for specialized AI support capabilities such as document processing experiments, evaluation tooling, and selected agent workflow components.
- Use a modern Python project configuration such as `pyproject.toml` when the developer approves the setup.
- Do not assume a specific framework, package manager, or Python version beyond the project's approved configuration.

## Module responsibilities
Potential responsibilities include:
- Document parsing and extraction support.
- Chunking and preprocessing experiments.
- Offline RAG and model evaluation.
- Evaluation datasets and reproducible scripts.
- Specialized AI workflows when a clear service boundary exists.

These are roadmap possibilities, not automatic implementation requirements.

## Copilot may help with
- Python project scaffolding and package layout.
- Virtual environment and dependency configuration.
- `pyproject.toml`, lint, formatting, and test-tool configuration.
- Module, class, function, protocol, and data-model skeletons.
- CLI entry-point scaffolding.
- Test fixtures and test structure.
- Explaining Python packaging, typing, dependency management, and test tools.

## Do not implement without developer-defined behavior
- Document processing rules, chunking strategies, or metadata semantics.
- Embedding selection, retrieval algorithms, ranking, or RAG orchestration.
- Evaluation scoring rules or quality thresholds.
- Model prompts, agent reasoning, or autonomous decision-making.
- Data retention, privacy, or external data-transfer behavior.

When behavior is unspecified, create only a skeleton with TODO markers or ask for clarification. Do not select AI libraries or model providers without explaining trade-offs and obtaining approval.

## Python conventions
- Prefer type hints and clear module boundaries.
- Keep functions focused and explicit.
- Avoid unnecessary frameworks and abstractions.
- Keep secrets in environment configuration, never source files.
- Make scripts reproducible and document required inputs.
- Do not silently add dependencies.

## Tests and explanation
- Use the test framework already selected by the project; if none exists, propose options first.
- Do not invent expected business outcomes for tests.
- Explain each created file, dependency, and command.
- Ask before modifying files, installing dependencies, or proceeding to another implementation step.
