#!/bin/bash
cd "$(dirname "$0")/src/main/resources"
mkdir -p "../../../output/"
exec zip -r /Users/ShaneBee/Desktop/Server/Skript/1-21-2/worlds/world/datapacks/Beer.zip . -x '**/.DS_Store'
exit
