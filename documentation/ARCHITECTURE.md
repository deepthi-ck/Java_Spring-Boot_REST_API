# Architecture

Monolithic Spring Boot REST application (Scenario 1), Java 8 only.

Stack: Spring Boot **2.7.x** on the `javax.*` namespace (Java 8 compatible; not Spring Boot 3 / Jakarta).

Layering follows common Spring Boot application layout:

- `RestApiApplication` — `@SpringBootApplication` entrypoint
- `controller` / web filters — HTTP API (`@RestController`)
- `service` — business logic
- `repository` / domain `model` — in-memory persistence and entities
- `config` — Spring configuration
- `exception`, `dto`, `mapper`, `util` — supporting layers

Externalized configuration lives under `src/main/resources` (`application.yaml`).
