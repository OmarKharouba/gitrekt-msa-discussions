FROM maven:latest

RUN apt-get update
RUN apt-get install -y inotify-tools

WORKDIR /usr/src/app

COPY "./pom.xml" .
RUN mvn -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY ./ .

RUN ["mvn", "clean", "install"]
