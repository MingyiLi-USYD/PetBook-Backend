# 使用 OpenJDK 11 作为基础镜像
FROM openjdk:11-jdk

# 设置维护者信息
LABEL maintainer=mingyi

# 定义环境变量 PARAMS，用于传递启动参数（您可以根据需要修改参数）
ENV PARAMS="--server.port=8080 --spring.profiles.active=prod --illegal-access=warn"

# 将构建好的 Spring Boot 应用程序 JAR 复制到容器中
COPY target/*.jar /app.jar

# 声明容器将监听的端口
EXPOSE 8080

# 定义容器启动命令
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar", "${PARAMS}"]
