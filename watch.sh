#!/bin/sh

# First Run
mvn compile
mvn exec:java &

MONITORDIR="src"
p=$!;
inotifywait -m -r -e create,close_write,move,delete,modify --format '%f' "${MONITORDIR}" | while read NEWFILE
do
    echo "${NEWFILE} Changed! Live Reloading....";
    kill -9 $p;
    mvn compile;
    mvn exec:java &
    p=$!;
done
