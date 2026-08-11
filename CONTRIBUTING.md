# Contributing

Thanks for contributing to this Spring Boot REST API (Java 8).

## Requirements

- JDK **8** only (see `.sdkmanrc`)
- Maven 3.9+ (or use `./mvnw` / `mvnw.cmd`)

## Build

```bash
./mvnw clean test
```

## Quality tools

Tools are integrated into the Maven build / scripts (not separate fixture modules):

CK, CPD, Checkstyle, Git, JaCoCo, OWASP-Dependency-Check, PIT, PMD, SpotBugs,
Static-DU-JaCoCo-composite, diff-cover.

See `documentation/TOOLS.md`.
