FROM adoptopenjdk/openjdk11
ARG JAR_FILE_PATH=target/*SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
# FROM openjdk:15-jdk-alpine
# COPY target/*SNAPSHOT.jar app.jar
# EXPOSE 8081
# ENV TZ=Asia/Seoul
# RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
# ENTRYPOINT ["java","-Xmx400M","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar","--spring.profiles.active=docker"]
