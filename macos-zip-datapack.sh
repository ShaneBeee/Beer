#!/bin/bash
cd "$(dirname "$0")/src/main/resources/datapack"
mkdir -p "../../../../output/"
echo "starting"
exec zip -r ../../../../output/Beer-DataPack.zip . -x '**/.DS_Store'
echo "test"
exit
