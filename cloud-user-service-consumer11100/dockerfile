FROM openjdk:11-jdk
LABEL maintainer=mingyi

#启动自行加载   服务名-prod.yml配置
ENV PARAMS="--server.port=8080 --spring.profiles.active=prod "

COPY target/*.jar /app.jar
EXPOSE 8080

# hospital-manage
ENTRYPOINT ["/bin/sh","-c","java -Djava.security.egd=file:/dev/./urandom -jar /app.jar ${PARAMS}"]