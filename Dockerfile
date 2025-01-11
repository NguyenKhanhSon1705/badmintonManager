# Sử dụng base image với Java 23
FROM eclipse-temurin:23-jre-alpine

# Tạo thư mục chứa ứng dụng
WORKDIR /app

# Copy file WAR vào container từ thư mục target
COPY target/badmintonManager-0.0.1-SNAPSHOT.war app.war

# Expose port của ứng dụng Spring Boot
EXPOSE 8888

# Chạy ứng dụng với file WAR
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.war"]
