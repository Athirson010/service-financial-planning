FROM maven:3.8-amazoncorretto-17
COPY . .
RUN mvn clean package

WORKDIR localtime
ENV TZ=America/Sao_Paulo
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV LANG=pt_br.UTF-8 LANGUAGE=pt_BR.UTF-8

#Download opentelemtry agent
FROM openjdk:17-alpine
WORKDIR ../
RUN wget -O opentelemetry-javaagent-all.jar 'https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar'
COPY target/*.jar .

EXPOSE 8080

ENTRYPOINT ["java","-javaagent:/opentelemetry-javaagent-all.jar","-jar", "./financialPlanning-0.0.1-SNAPSHOT.jar"]