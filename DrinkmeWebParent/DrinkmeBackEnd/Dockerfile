FROM openjdk:17
EXPOSE 8080



ADD /DrinkmeWebParent/DrinkmeBackEnd/target/isa-project-backend.jar isa-project-backend.jar


#RUN ./DrinkmeWebParent/mvnw clean package

ENTRYPOINT ["java", "-jar", "/isa-project-backend.jar"]



