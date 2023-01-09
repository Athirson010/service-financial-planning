FROM amazoncorretto:17-alpine
COPY target/*.jar .
WORKDIR localtime

ENV SERVICE_NAME financial-planning

ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV LANG=pt_br.UTF-8 LANGUAGE=pt_BR.UTF-8
EXPOSE 8080

WORKDIR app
ADD src/main/resources/agent-opentelemetry .

ENV CAM_OPTL "opentelemetry-javaagent-all.jar"
EXPOSE 4317
ENV JAVA_TOOL_OPTIONS -javaagent:$CAM_OPTL -Dotel.exporter.otlp.endpoint=http://localhost:4317 -Dotel.resource.attributes=service.name=$SERVICE_NAME
RUN echo java $JAVA_TOOL_OPTIONS, -jar financialPlanning-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java $JAVA_TOOL_OPTIONS" ,"-jar", "financialPlanning-0.0.1-SNAPSHOT.jar"]



