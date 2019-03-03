#!/bin/sh

mvn compile
mvn exec:java &
MONITORDIR="src"
inotifywait -m -r -e create,close_write,move,delete,modify --format '%f' "${MONITORDIR}" | while read NEWFILE
do
    echo "${NEWFILE} Changed! Live Reloading....";
    kill -9 $(ps aux | grep 'java' | awk '{print $2}');
    mvn compile;
    mvn exec:java &
    p=$!;
done