FROM openjdk:17-oracle

LABEL authors="jaba"

WORKDIR /app

COPY target/ .

CMD ["java", "-jar", "FistLab-0.0.1-SNAPSHOT.jar"]