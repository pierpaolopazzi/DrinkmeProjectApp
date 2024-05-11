# Immagine base con openjdk:17
FROM openjdk:17

# Espone la porta 8080
EXPOSE 8080

# Espone la directory di lavoro all'interno del container
WORKDIR /app

# Copia il jar del frontend all'interno dell'immagine Docker
ADD target/isa-project-backend.jar isa-project-backend.jar

# Variabili d'ambiente per la connessione al database PostgreSQL
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/drinkmedb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password

# Avvio applicazione all'interno del container
ENTRYPOINT ["java", "-jar", "/app/isa-project-backend.jar"]



