---
applyTo: "infrastructure/**"
---

# ZANMI AI Infrastructure — Module Instructions

## Technology context
This directory is reserved for local development, packaging, deployment, and infrastructure configuration. Docker/Compose may be introduced before Kubernetes/AKS. Do not assume that any infrastructure tool is already selected or required for the current story.

## Copilot may help with
- Directory and configuration-file scaffolding.
- Dockerfile and Compose skeletons after approval.
- Placeholder environment files and configuration templates.
- Kubernetes manifest or Helm chart skeletons only when explicitly requested.
- Scripts for repeatable, non-destructive local developer tasks.
- Explaining container, network, volume, deployment, and orchestration configuration.

## Do not independently
- Provision or modify cloud resources.
- Deploy, publish, or expose services.
- Create databases, networks, storage, identities, or access policies.
- Select production infrastructure or cloud architecture.
- Add secrets, real credentials, or environment-specific private values.
- Run destructive commands, migrations, cleanup, or resource deletion.
- Invent resource sizing, security policies, ingress rules, or network access.

## Safety requirements
- Prefer local and non-production examples.
- Use placeholders for secrets and environment-specific values.
- Explain exposed ports, mounted volumes, persistent data, permissions, and network access.
- Clearly identify commands that create, modify, or delete resources.
- Ask for explicit approval before running any command with side effects.
- Never run destructive or production-impacting commands without explicit, specific approval.

## Approval and teaching
Before creating infrastructure configuration, explain what the resource does, why it is needed now, which files will change, and the impact of running it. Ask for approval. Implement only the approved configuration, explain it, provide a safe validation command, and stop for the next instruction.
