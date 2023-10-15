# Bước 1: Xây dựng ứng dụng Java bằng Maven
FROM maven:3.8.4-openjdk-17-slim as maven_builder
WORKDIR /app
COPY . /app
RUN mvn clean package -f /app/pom.xml

# Bước 2: Chạy ứng dụng trên Tomcat
FROM tomcat:9.0.80-jdk17
WORKDIR /usr/local/tomcat/webapps
COPY --from=maven_builder /app/target/demo112-1.0-SNAPSHOT.war app.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
