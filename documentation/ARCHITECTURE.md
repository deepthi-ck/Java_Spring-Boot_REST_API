# Architecture

Monolithic Spring Boot REST application (Scenario 1), Java 17 only.

Layering follows common Spring Boot application layout:

- `RestApiApplication` — `@SpringBootApplication` entrypoint
- `controller` / web filters — HTTP API (`@RestController`)
- `service` — business logic
- `repository` / domain `model` — in-memory persistence and entities
- `config` — Spring configuration
- `exception`, `dto`, `mapper`, `util` — supporting layers

Externalized configuration lives under `src/main/resources` (`application.yaml`).
