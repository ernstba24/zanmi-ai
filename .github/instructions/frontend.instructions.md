---
applyTo: "zanmi-frontend/**"
---

# ZANMI AI Frontend — Module Instructions

## Technology context
The planned frontend is React with TypeScript. Confirm the framework version, build tool, styling approach, routing, and component strategy with the developer before generating the application. Do not assume a UI library.

## Module responsibilities
The frontend will eventually provide user-facing experiences for chat, personal knowledge, engineering and learning workspaces, productivity, and workflow approvals. Implement only the currently approved screen or component.

## Copilot may help with
- Frontend project scaffolding and build configuration.
- TypeScript and React configuration.
- Component, page, route, and type/interface skeletons.
- Basic application entry point and development scripts.
- Accessibility and test-tool configuration.
- API client configuration skeletons when the API contract is provided.
- Explaining React, TypeScript, state, props, routing, and build tooling.

## Do not implement without developer-defined behavior
- Product workflows, user journeys, or interaction rules.
- Application state transitions or business validation.
- API contracts or assumptions about backend response shapes.
- Authentication/authorization behavior.
- Design system, visual branding, or component-library choices.
- Data persistence, analytics, or external integrations.

Do not invent UI requirements. Ask for the screen's purpose, user actions, data contract, and acceptance criteria when missing.

## Frontend conventions
- Use TypeScript rather than untyped JavaScript unless approved otherwise.
- Keep components focused and accessible.
- Do not hardcode secrets or private endpoints.
- Avoid unnecessary dependencies.
- Do not add mock business behavior that could be mistaken for a real implementation; clearly label approved placeholders.

## Approval and teaching
Before generating or modifying files, explain the proposed component structure, dependencies, and files affected, then ask for approval. After implementation, explain the code and verification steps and wait before proceeding.
