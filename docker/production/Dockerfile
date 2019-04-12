FROM maven:alpine

WORKDIR /usr/src/app

COPY "./pom.xml" .
RUN mvn -B -f ./pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY ./ .

RUN ["mvn", "clean", "install", "-DskipTests"]

EXPOSE ["3001"]
CMD ["mvn", "exec:java"]