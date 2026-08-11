# Java Spring Boot REST API

Spring Boot REST API application for **Java 17 only**.

Inspired by the layout conventions of [spring-projects/spring-boot](https://github.com/spring-projects/spring-boot)
(`.sdkmanrc`, `.editorconfig`, `config/`, `documentation/`, wrapper, CI) and the official
[Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/) application shape —
while remaining a single product REST API (not the Spring Boot framework monorepo).

## Requirements

- JDK **17** (see `.sdkmanrc`)
- Maven Wrapper (`./mvnw` / `mvnw.cmd`) — no global Maven required

## Project structure

```
.
├── .github/workflows/          CI (JDK 17)
├── .mvn/wrapper/               Maven Wrapper
├── .sdkmanrc                   Java 17 pin
├── .editorconfig
├── config/                     Checkstyle / PMD / SpotBugs (like Spring Boot config/)
├── documentation/              Architecture, API, tools
├── scripts/
│   ├── ck/                     CK runner
│   ├── git/                    Git churn metrics
│   └── tools/                  Aggregate quality pipeline
├── src/main/java/.../restapi/  Spring Boot application + REST layers
├── src/main/resources/         application.yaml (+ profiles)
├── src/test/java/              Tests
├── mvnw / mvnw.cmd
└── pom.xml                     Spring Boot 3.2 + quality plugins
```

## Building from source

```bash
./mvnw clean test
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd clean test
mvnw.cmd spring-boot:run
```

Health: `http://localhost:8080/api/health`

## Quality tools (integrated)

See `documentation/TOOLS.md`. All of: CK, CPD, Checkstyle, Git, JaCoCo,
OWASP-Dependency-Check, PIT, PMD, SpotBugs, Static-DU-JaCoCo-composite, diff-cover.

```bash
bash scripts/tools/run_tools.sh
```

## License

Apache License 2.0 — see `LICENSE.txt`.
