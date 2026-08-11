# Java Spring Boot REST API — Java 25

Single-module Spring Boot REST API (`java.version=25`, Spring Boot 3.5.5).
Tools are wired into this project (Maven + `config/` + `scripts/`) — **not** separate tool folders.

Tool versions follow [Golden_Repo_Lite Java-25](https://github.com/testable-platform/Golden_Repo_Lite/tree/java/Java-25)
(JaCoCo 0.8.12, Checkstyle config aligned with Golden). Full required tool set is still integrated in `pom.xml`.

## Structure

```
pom.xml
config/checkstyle|pmd|spotbugs/
scripts/
docs/
src/main/java/com/example/restapi/
  analytics|audit|catalog|config|controller|dto|event|exception
  mapper|model|notification|payment|policy|report|repository
  runtime|security|service|support|util|validation|warehouse|web
src/main/resources/ (+ i18n, static)
src/test/java/...
```

## APIs

- `/api/items` `/api/products` `/api/categories` `/api/customers`
- `/api/orders` `/api/shipments` `/api/payments` `/api/notifications`
- `/api/warehouses` `/api/catalog/brands` `/api/audit` `/api/analytics`
- `/api/policies` `/api/reports` `/api/events` `/api/runtime` `/api/stats/summary` `/api/health`

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
