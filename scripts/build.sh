#!/bin/bash
set -e

SCRIPT_DIR="$(dirname "$0")"

cd "$SCRIPT_DIR/../"

./mvnw clean install

docker build -t kauanmocelin/smartnewstracker .