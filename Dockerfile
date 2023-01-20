FROM openjdk:17-alpine
ENV SERVICE_NAME "financial-planning"

WORKDIR localtime
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV LANG=pt_br.UTF-8 LANGUAGE=pt_BR.UTF-8

#Download opentelemtry agent
WORKDIR ../
RUN wget -O opentelemetry-javaagent-all.jar 'https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar'
COPY target/*.jar .

EXPOSE 8080
EXPOSE 4317
ENTRYPOINT ["java","-javaagent:/opentelemetry-javaagent-all.jar", "-Dotel.exporter.otlp.endpoint=https://20fd5b4c700d4377b7b1b97229539656.apm.us-central1.gcp.cloud.es.io:443", "-Dotel.resource.attributes.service.name=financial-planning", "-Dotel.exporter.otlp.metrics.headers=Authorization=Bearer TI8chvPYR6u86v0QV", "-Dotel.metrics.exporter=otlp", "-Dotel.logs.exporter=otlp" , "-jar", "./financialPlanning-0.0.1-SNAPSHOT.jar"]
