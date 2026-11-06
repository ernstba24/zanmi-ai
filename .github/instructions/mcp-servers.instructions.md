---
applyTo: "zanmi-mcp-servers/**"
---

# ZANMI AI MCP Servers — Module Instructions

## Technology context
This module will contain Model Context Protocol (MCP) servers and narrowly scoped tool integrations. The initial implementation is expected to use Python and an MCP SDK, but confirm the selected SDK, version, and transport with the developer before setup.

## Module responsibilities
- Expose explicitly approved capabilities through MCP tools.
- Keep each server's purpose and tool surface narrow.
- Document tool names, input schemas, outputs, permissions, and operational requirements.
- Support clear validation, error handling, and observability.

## Copilot may help with
- Server project scaffolding.
- SDK and transport configuration after approval.
- Tool registration boilerplate and typed input/output schema skeletons.
- Configuration templates and environment-variable placeholders.
- Logging and test scaffolding.
- Protocol concept explanations and example request/response structures.

## Do not implement without developer-defined behavior
- Tool business logic or decisions.
- File, repository, database, email, calendar, or other external-system actions.
- Permission scopes or authorization policy.
- Tool side effects, write operations, or autonomous execution.
- Agent planning, tool selection, or approval policy.

Do not invent tools or expose capabilities just because an SDK makes them possible. Ask the developer to define the intended capability, input, output, access scope, and whether it is read-only or state-changing.

## Security requirements
- Default to read-only tools.
- Validate all tool inputs.
- Apply least privilege and explicit allowlists.
- Treat tool inputs and outputs as untrusted.
- Never expose arbitrary shell, filesystem, SQL, or network access.
- Never embed credentials in code or logs.
- Explain risks and obtain approval before introducing any state-changing integration.
- Consequential actions must have a developer-defined human approval boundary.

## Approval and teaching
Explain the MCP concepts, files, schemas, transport, and security implications before implementation. Ask for explicit approval before creating or changing files, dependencies, tools, or permissions. Implement one approved step at a time, explain the result, and stop.
