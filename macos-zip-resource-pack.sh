#!/bin/bash
cd "$(dirname "$0")/src/main/resources/resource-pack"
mkdir -p "../../../../output/"
echo "starting"
exec zip -r ../../../../output/Beer-ResourcePack.zip . -x '**/.DS_Store'
echo "test"
exit
