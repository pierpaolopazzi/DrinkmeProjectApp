# Immagine base con openjdk:17
FROM openjdk:17

# Espone la porta 8080
EXPOSE 8080

# Espone la directory di lavoro all'interno del container
WORKDIR /app

# Copia il jar del frontend all'interno dell'immagine Docker
COPY ./target/isa-project-backend.jar isa-project-backend.jar

# Avvio applicazione all'interno del container
ENTRYPOINT ["java", "-jar", "/app/isa-project-backend.jar"]



