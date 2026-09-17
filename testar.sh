#!/usr/bin/env bash

set -euo pipefail

cd "$(dirname "$0")"

if command -v mvn >/dev/null 2>&1; then
    mvn --quiet clean verify
    java -jar target/teste-pratico-iniflex-1.0.0.jar
    exit 0
fi

echo "Maven não encontrado; executando somente o programa com o JDK."
mkdir -p target/classes

COMPILADOR=(javac)
if ! command -v javac >/dev/null 2>&1; then
    COMPILADOR=(java -m jdk.compiler/com.sun.tools.javac.Main)
fi

"${COMPILADOR[@]}" -source 17 -target 17 -Xlint:all,-options -Werror \
    -d target/classes \
    $(find src/main/java -name '*.java')

java -cp target/classes br.com.iniflex.Principal
