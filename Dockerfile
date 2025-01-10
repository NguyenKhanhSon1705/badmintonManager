# Sử dụng image JDK 17
FROM openjdk:23-jdk-slim

# Tạo thư mục làm việc
WORKDIR /app

# Copy file JAR vào container
COPY target/your-application.jar app.jar

# Expose cổng 8080
EXPOSE 8888

# Command chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
