FROM gradle:8.13-alpine AS build
WORKDIR /opt/app/
COPY . .
RUN gradle clean build -x test

FROM eclipse-temurin:21
WORKDIR /opt/app/

COPY --from=build /opt/app/build/libs/energy-consumption-app-0.0.1-SNAPSHOT.jar /opt/app/app.jar

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
EXPOSE 8080
