#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"
mvn -q clean test jacoco:report
mvn -q checkstyle:check pmd:check pmd:cpd-check spotbugs:check
mvn -q org.pitest:pitest-maven:mutationCoverage || true
mvn -q org.owasp:dependency-check-maven:check || true
mvn -q diff-coverage:report || true
mvn -q verify -Pstatic-du-jacoco-composite || true
python scripts/git_churn.py || true
echo "Tool pipeline finished (Java 11)."
