FROM openjdk:8-jre-alpine
COPY target/well-orchestrator-1.0.0.jar well-orchestrator-1.0.0.jar
ENTRYPOINT ["java","-jar","/well-orchestrator-1.0.0.jar"]