# Spring Boot REST API — Java 8

Scenario 1 (Monolithic) · flat (single module) · Customer Version **8**

Tool fixtures mirror [Golden_Repo_Lite / Java-8](https://github.com/testable-platform/Golden_Repo_Lite/tree/java/Java-8),
plus **Git** and **Static-DU-JaCoCo-composite** adapted for JDK 8.

| Tool | Folder | Run |
|------|--------|-----|
| CK | `CK/` | `bash run_ck.sh` |
| CPD | `CPD/` | `bash run_cpd.sh` |
| Checkstyle | `Checkstyle/` | `bash run_checkstyle.sh` |
| Git | `Git/` | `python run_pydriller.py` |
| JaCoCo | `JaCoCo/` | `bash run_jacoco.sh` |
| OWASP-Dependency-Check | `OWASP-Dependency-Check/` | `bash run_owasp_dc.sh` |
| PIT | `PIT/` | `bash run_pit.sh` |
| PMD | `PMD/` | `bash run_pmd.sh` |
| SpotBugs | `SpotBugs/` | `bash run_spotbugs.sh` |
| Static-DU-JaCoCo-composite | `Static-DU-JaCoCo-composite/` | `bash run_composite.sh` |
| diff-cover | `diff-cover/` | `bash run_diff_cover.sh` |

Each folder has `trigger.yaml` for Testable execution.
