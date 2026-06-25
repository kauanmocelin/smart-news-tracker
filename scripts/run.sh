#!/bin/bash
set -e

SCRIPT_DIR="$(dirname "$0")"

"$SCRIPT_DIR"/build.sh

cd "$SCRIPT_DIR/../"

docker compose up -d