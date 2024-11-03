FROM openjdk:17-oracle

LABEL authors="jaba"

COPY target/ .


CMD ["java", "-jar", "FistLab-0.0.1-SNAPSHOT.jar"]
