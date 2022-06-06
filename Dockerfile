FROM amazoncorretto:17 as builder
ARG JAR_FILE=build/libs/*SNAPSHOT.jar
COPY ${JAR_FILE} application.jar

RUN java -Djarmode=layertools -jar application.jar extract

FROM amazoncorretto:17
COPY --from=builder dependencies/ ./
COPY --from=builder snapshot-dependencies/ ./

RUN true

COPY --from=builder spring-boot-loader/ ./


COPY --from=builder application/ ./
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "org.springframework.boot.loader.JarLauncher"]