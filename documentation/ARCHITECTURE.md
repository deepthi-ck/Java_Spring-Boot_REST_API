# Architecture

Monolithic Spring Boot REST application (Scenario 1), Java 21 only.

Layering follows common Spring Boot application layout:

- `RestApiApplication` — `@SpringBootApplication` entrypoint
- `web` / `controller` — HTTP API (`@RestController`) and servlet filters
- `service` — business logic
- `repository` / domain `model` — in-memory persistence and entities
- `config` — Spring configuration
- `security`, `validation`, `support` — cross-cutting concerns

Externalized configuration lives under `src/main/resources` (`application.yaml`).
