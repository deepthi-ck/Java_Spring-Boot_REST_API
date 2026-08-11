# Java Spring Boot REST API — Java 17

Single-module Spring Boot REST API (`java.version=17`, Spring Boot 3.2).
Tools are wired into this project (Maven + `config/` + `scripts/`) — **not** separate tool folders.

Tool versions follow [Golden_Repo_Lite Java-17](https://github.com/testable-platform/Golden_Repo_Lite/tree/java/Java-17).

## Structure

```
pom.xml
config/checkstyle|pmd|spotbugs/
scripts/git_churn.py, run_ck.sh, run_tools.sh
src/main/java/com/example/restapi/
  controller|service|repository|model|dto|mapper|exception|config|util
src/test/java/...
```

## APIs

- `/api/items` CRUD + stock adjust
- `/api/products` CRUD
- `/api/categories` CRUD
- `/api/customers` CRUD
- `/api/orders` create/list/status
- `/api/shipments` create/list
- `/api/stats/summary`
- `/api/health`

## Tools (Java 17)

| Tool | Integration | Command |
|------|-------------|---------|
| CK | `scripts/run_ck.sh` + profile `ck` | `bash scripts/run_ck.sh` |
| Checkstyle | plugin + `config/checkstyle/` | `mvn checkstyle:check` |
| PMD | plugin + `config/pmd/ruleset.xml` | `mvn pmd:check` |
| CPD | PMD plugin | `mvn pmd:cpd-check` |
| SpotBugs | plugin + `config/spotbugs/` | `mvn spotbugs:check` |
| JaCoCo | plugin 0.8.12 | `mvn test jacoco:report` |
| PIT | pitest-maven 1.17.0 | `mvn org.pitest:pitest-maven:mutationCoverage` |
| OWASP-Dependency-Check | 10.0.4 | `mvn org.owasp:dependency-check-maven:check` |
| diff-cover | diff-coverage plugin | `mvn diff-coverage:report` |
| Git | `scripts/git_churn.py` | `python scripts/git_churn.py` |
| Static-DU-JaCoCo-composite | profile | `mvn verify -Pstatic-du-jacoco-composite` |

```bash
mvn clean verify
mvn spring-boot:run
```
