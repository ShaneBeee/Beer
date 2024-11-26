#!/bin/bash
cd "$(dirname "$0")/src/main/resources"
mkdir -p "../../../output/"
echo "starting"
exec zip -r ../../../output/Beer.zip . -x '**/.DS_Store'
echo "test"
exit
