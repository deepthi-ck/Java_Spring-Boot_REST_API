# Java Spring Boot REST API — Java 8

Single-module Spring Boot REST API (`java.version=1.8`).  
Tools are wired into this project (Maven plugins + `config/` + `scripts/`) — **not** separate tool folders.

## Project structure

```
pom.xml
config/
  checkstyle/checkstyle.xml
  pmd/ruleset.xml
  pmd/static-du-ruleset.xml
scripts/
  git_churn.py
src/main/java/...
src/test/java/...
```

## REST API

| Method | Path | Description |
|--------|------|-------------|
| GET | `/api/items` | List items |
| GET | `/api/items/{id}` | Get item |
| POST | `/api/items` | Create item |
| PUT | `/api/items/{id}` | Update item |
| DELETE | `/api/items/{id}` | Delete item |
| GET | `/api/items/stats/total` | Total price |

```bash
mvn spring-boot:run
```

## Tools (Java 8)

| Tool | How it is included | Command |
|------|--------------------|---------|
| **Checkstyle** | `maven-checkstyle-plugin` + `config/checkstyle/` | `mvn checkstyle:check` |
| **PMD** | `maven-pmd-plugin` + `config/pmd/ruleset.xml` | `mvn pmd:check` |
| **CPD** | same PMD plugin (`cpd-check`) | `mvn pmd:cpd-check` |
| **SpotBugs** | `spotbugs-maven-plugin` | `mvn spotbugs:check` |
| **JaCoCo** | `jacoco-maven-plugin` | `mvn test jacoco:report` |
| **PIT** | `pitest-maven` | `mvn org.pitest:pitest-maven:mutationCoverage` |
| **OWASP-Dependency-Check** | `dependency-check-maven` | `mvn org.owasp:dependency-check-maven:check` |
| **diff-cover** | `diff-coverage-maven-plugin` | `mvn diff-coverage:report` |
| **Git** | `scripts/git_churn.py` (PyDriller) | `python scripts/git_churn.py` |
| **Static-DU-JaCoCo-composite** | profile `static-du-jacoco-composite` | `mvn verify -Pstatic-du-jacoco-composite` |

Full verify (Checkstyle, PMD/CPD, SpotBugs, JaCoCo):

```bash
mvn clean verify
```
