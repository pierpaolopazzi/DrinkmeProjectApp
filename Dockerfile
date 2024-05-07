FROM openjdk:17
EXPOSE 8080
ADD /DrinkmeWebParent/DrinkmeBackEnd/target/isa-project.jar isa-project.jar
ENTRYPOINT ["java", "-jar", "/isa-project.jar"]