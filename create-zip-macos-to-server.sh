#!/bin/bash
cd "$(dirname "$0")/Beer"
mkdir -p "../output/"
exec zip -r ../../../desktop/Server/Skript/1-21-2/worlds/world/datapacks/Beer.zip . -x '**/.DS_Store'
exit
