# Immagine base con openjdk:17
FROM openjdk:17

# Espone la porta 80
EXPOSE 80

# Imposta la directory di lavoro all'interno del container
#WORKDIR /app

# Copia il jar del frontend nell'immagine Docker
ADD target/isaprojectfrontend.jar isaprojectfrontend.jar

# Variabili d'ambiente per la connessione al database PostgreSQL
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/drinkmedb
ENV SPRING_DATASOURCE_USERNAME=postgres
ENV SPRING_DATASOURCE_PASSWORD=password

# Avvio applicazione nel container
#ENTRYPOINT ["java", "-jar", "/app/isa-project-frontend.jar"]
ENTRYPOINT ["java", "-jar", "/isaprojectfrontend.jar"]