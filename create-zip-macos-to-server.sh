#!/bin/bash
cd "$(dirname "$0")/src/main/resources"
exec zip -r /Users/ShaneBee/Desktop/Server/Skript/1-21-4/worlds/world/datapacks/Beer.zip . -x '**/.DS_Store'
exit
