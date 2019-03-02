#!/bin/sh

if [[ -z "${DEPLOY_ENV}" ]]; then
    # First Run
    mvn compile
    mvn exec:exec &
    MONITORDIR="src"
    inotifywait -m -r -e create,close_write,move,delete,modify --format '%f' "${MONITORDIR}" | while read NEWFILE
    do
        echo "${NEWFILE} Changed! Live Reloading....";
        kill -9 $(ps aux | grep 'java' | awk '{print $2}');
        mvn compile;
        mvn exec:exec &
        p=$!;
    done
else
    mvn test
fi
