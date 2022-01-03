FROM openjdk:17-oracle
WORKDIR /app

COPY target/dependency /app/dependency
COPY target/conference-service-1.0.0.jar /app/

ENTRYPOINT ["java", "-cp", "./*:./dependency/*", "org.example.Main"]