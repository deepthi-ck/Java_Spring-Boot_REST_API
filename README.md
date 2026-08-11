# Java Spring Boot REST API — Java 21

Single-module Spring Boot REST API (`java.version=21`, Spring Boot 3.3.6).
Tools are wired into this project (Maven + `config/` + `scripts/`) — **not** separate tool folders.

Tool versions follow [Golden_Repo_Lite Java-21](https://github.com/testable-platform/Golden_Repo_Lite/tree/java/Java-21)
(JaCoCo 0.8.12, PIT 1.17.0, OWASP Dependency-Check 10.0.4).

## Structure

```
pom.xml
config/checkstyle|pmd|spotbugs/
scripts/
src/main/java/com/example/restapi/
  controller|service|repository|model|dto|mapper|exception|config|util|event|security|validation
src/test/java/...
```

## APIs

- `/api/items` `/api/products` `/api/categories` `/api/customers`
- `/api/orders` `/api/shipments` `/api/stats/summary` `/api/events` `/api/health`

## Tools (Java 21)

| Tool | Command |
|------|---------|
| CK | `bash scripts/run_ck.sh` |
| Checkstyle | `mvn checkstyle:check` |
| PMD / CPD | `mvn pmd:check pmd:cpd-check` |
| SpotBugs | `mvn spotbugs:check` |
| JaCoCo | `mvn test jacoco:report` |
| PIT | `mvn org.pitest:pitest-maven:mutationCoverage` |
| OWASP-Dependency-Check | `mvn org.owasp:dependency-check-maven:check` |
| diff-cover | `mvn diff-coverage:report` |
| Git | `python scripts/git_churn.py` |
| Static-DU-JaCoCo-composite | `mvn verify -Pstatic-du-jacoco-composite` |

```bash
mvn clean verify
```
