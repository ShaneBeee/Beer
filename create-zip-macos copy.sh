#!/bin/bash
cd "$(dirname "$0")/Beer"
exec zip -r ../Beer.zip . -x '**/.DS_Store'
exit