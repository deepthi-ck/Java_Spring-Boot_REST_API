# Java Spring Boot REST API — Java 25

Single-module Spring Boot REST API (`java.version=25`, Spring Boot 3.5.5).
Tools are wired into this project (Maven + `config/` + `scripts/`) — **not** separate tool folders.

Tool versions follow [Golden_Repo_Lite Java-25](https://github.com/testable-platform/Golden_Repo_Lite/tree/java/Java-25)
where available (JaCoCo 0.8.12, Checkstyle). Remaining tools from the shared set are integrated the same way as other JDK branches.

Golden Java-25 currently lists Checkstyle, JaCoCo, and ba-dua fixtures; this project still wires the full requested tool set into one REST API.

## Structure

```
pom.xml
config/checkstyle|pmd|spotbugs/
scripts/
src/main/java/com/example/restapi/
  controller|service|repository|model|dto|mapper|exception|config|util|event|security|validation|runtime
src/test/java/...
```

## APIs

- `/api/items` `/api/products` `/api/categories` `/api/customers`
- `/api/orders` `/api/shipments` `/api/stats/summary` `/api/events` `/api/health` `/api/runtime`

## Tools (Java 25)

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
