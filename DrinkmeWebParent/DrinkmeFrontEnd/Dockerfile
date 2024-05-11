# Immagine base con openjdk:17
FROM openjdk:17

# Espone la porta 80
EXPOSE 80

# Imposta la directory di lavoro all'interno del container
WORKDIR /app

# Copia il jar del frontend nell'immagine Docker
COPY ./target/isa-project-frontend.jar isa-project-frontend.jar

# Avvio applicazione nel container
ENTRYPOINT ["java", "-jar", "/app/isa-project-frontend.jar"]