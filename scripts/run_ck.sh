#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$ROOT"
mkdir -p ck-reports
if [[ ! -f ck.jar ]]; then
  echo "Download CK jar as ck.jar (mauricioaniche/ck) then re-run."
  echo "Example: curl -L -o ck.jar <ck-release-jar-url>"
  exit 1
fi
java -jar ck.jar src/main/java false 0 false ./ck-reports
