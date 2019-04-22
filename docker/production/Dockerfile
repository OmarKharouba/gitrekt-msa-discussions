# select image
FROM maven:3.6.1-jdk-13-alpine

# copy the project files
COPY ./pom.xml ./pom.xml

# build all dependencies for offline use
RUN mvn dependency:go-offline -B

# copy your other files
COPY ./src ./src

# build for release
RUN ["mvn", "compile"]

CMD ["mvn", "exec:java"]

# set the startup command to run your binary
# CMD ["java", "-jar", "./target/media-server-1.0-SNAPSHOT-jar-with-dependencies.jar"]