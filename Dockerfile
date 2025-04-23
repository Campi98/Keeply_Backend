# Build stage
#FROM eclipse-temurin:21-jdk-alpine AS build
#WORKDIR /app
#COPY build.gradle.kts settings.gradle.kts ./
#COPY gradle ./gradle
#COPY gradlew ./
#COPY src ./src
#RUN chmod +x ./gradlew
#RUN ./gradlew bootJar --no-daemon

# Run stage
#FROM eclipse-temurin:21-jre-alpine
#WORKDIR /app
#COPY --from=build /app/build/libs/app.jar .
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]