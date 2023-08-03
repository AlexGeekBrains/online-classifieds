
FROM openjdk:17-jdk-alpine
WORKDIR /app

COPY target/online-classifieds-0.0.1-SNAPSHOT.jar /app/online-classifieds-0.0.1-SNAPSHOT.jar
CMD ["java", "-jar", "online-classifieds-0.0.1-SNAPSHOT.jar"]