FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/build/libs/app.jar"]