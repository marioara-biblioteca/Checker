FROM maven:latest AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:17
COPY --from=build /home/app/target/*.jar /usr/local/lib/checker.jar
EXPOSE 8090:8090
ENTRYPOINT ["java","-jar","/usr/local/lib/checker.jar"]
