#!/bin/bash
cd "$(dirname "$0")/Beer"
mkdir -p "../output/"
exec zip -r ../output/Beer.zip . -x '**/.DS_Store'
exit
