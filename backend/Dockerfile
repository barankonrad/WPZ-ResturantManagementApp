FROM eclipse-temurin:21-jdk-alpine
LABEL authors="konradbaran"

COPY target/restaurantBackend.jar /app/restaurantBackend.jar
WORKDIR /app

EXPOSE 8080

CMD ["java", "-jar", "restaurantBackend.jar"]