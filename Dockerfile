FROM openjdk:17-slim
FROM openjdk:11-jre

COPY target/*.jar .
WORKDIR localtime

#ENV SERVICE_NAME "financial-planning"

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV LANG=pt_br.UTF-8 LANGUAGE=pt_BR.UTF-8
EXPOSE 8080

RUN wget -O opentelemetry-javaagent-all.jar 'https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar'
EXPOSE 4317

ENTRYPOINT ["java -javaagent:opentelemetry-javaagent-all.jar -Dotel.exporter.otlp.endpoint=http://localhost:4317 -Dotel.resource.attributes=service.name=financial-planning" ,"-jar", "financialPlanning-0.0.1-SNAPSHOT.jar"]

# java -javaagent:/home/user/Documentos/financialPlanning/src/main/resources/agent-opentelemetry/opentelemetry-javaagent-all.jar -Dotel.exporter.otlp.endpoint=http://localhost:4317 -Dotel.resource.attributes=service.name=financial-planning, -jar financialPlanning-0.0.1-SNAPSHOT.jar
