FROM openjdk:8
EXPOSE 8080
ADD target/isa-project.jar isa-project.jar
ENTRYPOINT ["java", "-jar", "/isa-project.jar"]