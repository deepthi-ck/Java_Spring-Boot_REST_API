#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")" && pwd)"
cd "$ROOT"
mvn test jacoco:report
pmd check -d DataFlowSample.java -R category/java/bestpractices.xml/UnusedAssignment -f text || true
