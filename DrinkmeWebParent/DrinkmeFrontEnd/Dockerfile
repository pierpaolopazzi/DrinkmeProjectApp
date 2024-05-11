# Immagine base con openjdk:17
FROM openjdk:17

# Espone la porta 80
EXPOSE 80

# Imposta la directory di lavoro all'interno del container
WORKDIR /app

# Copia il jar del frontend nell'immagine Docker
COPY /DrinkmeWebParent/DrinkmeFrontEnd/target/isa-project-frontend.jar isa-project-frontend.jar

# Avvio applicazione nel container
ENTRYPOINT ["java", "-jar", "/isa-project-frontend.jar"]