## -------------------------------
## Stage 1: Build
## -------------------------------
#FROM eclipse-temurin:21-jdk AS build
#
#WORKDIR /app
#
## Copy only pom.xml first to leverage Docker cache for dependencies
#COPY pom.xml mvnw ./
#COPY .mvn .mvn
#
## Download dependencies only (faster builds on subsequent builds)
#RUN ./mvnw dependency:go-offline
#
## Copy the source code
#COPY src ./src
#
## Build the jar (skip tests to speed up)
#RUN ./mvnw clean package -DskipTests
#
## -------------------------------
## Stage 2: Runtime
## -------------------------------
#
## Option 1: Standard JRE (stable)
## FROM eclipse-temurin:21-jre AS runtime
#
## Option 2: Alpine JRE (smaller, ~50MB) ⚠️ check compatibility first
# FROM eclipse-temurin:21-jre-alpine AS runtime
#
#WORKDIR /app
#
## Copy only the built jar
#COPY --from=build /app/target/*.jar app.jar
#
## Expose the port your app uses
#EXPOSE 8080
#
## Run the app
#ENTRYPOINT ["java","-jar","app.jar"]



#FROM eclipse-temurin:21-jdk-jammy as deps
#WORKDIR /build
#COPY --chmod=0755 mvnw mvnw
#COPY .mvn/ .mvn/
#RUN --mount=type=bind,source=pom.xml,target=pom.xml \
#    --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests
#
#FROM deps as package
#WORKDIR /build
#COPY ./src src/
#RUN --mount=type=bind,source=pom.xml,target=pom.xml \
#    --mount=type=cache,target=/root/.m2 \
#    ./mvnw package -DskipTests && \
#    mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar target/app.jar
#
#FROM package as extract
#WORKDIR /build
#RUN java -Djarmode=layertools -jar target/app.jar extract --destination target/extracted
#
#FROM extract as development
#WORKDIR /build
#RUN cp -r /build/target/extracted/dependencies/. ./
#RUN cp -r /build/target/extracted/spring-boot-loader/. ./
#RUN cp -r /build/target/extracted/snapshot-dependencies/. ./
#RUN cp -r /build/target/extracted/application/. ./
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000
#CMD [ "java", "-Dspring.profiles.active=dev", "org.springframework.boot.loader.launch.JarLauncher" ]
#
#FROM eclipse-temurin:21-jre-jammy AS final
#ARG UID=10001
#RUN adduser \
#    --disabled-password \
#    --gecos "" \
#    --home "/nonexistent" \
#    --shell "/sbin/nologin" \
#    --no-create-home \
#    --uid "${UID}" \
#    appuser
#USER appuser
#COPY --from=extract build/target/extracted/dependencies/ ./
#COPY --from=extract build/target/extracted/spring-boot-loader/ ./
#COPY --from=extract build/target/extracted/snapshot-dependencies/ ./
#COPY --from=extract build/target/extracted/application/ ./
#EXPOSE 8080
#ENTRYPOINT [ "java", "-Dspring.profiles.active=dev", "org.springframework.boot.loader.launch.JarLauncher" ]



# -----------------------------
# Stage 1: Build Jar
# -----------------------------
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app

# Copy Maven wrapper and config
COPY --chmod=0755 mvnw mvnw
COPY .mvn/ .mvn/
COPY pom.xml .

# Download dependencies
RUN --mount=type=cache,target=/root/.m2 ./mvnw dependency:go-offline -DskipTests

# Copy source and build jar
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 ./mvnw package -DskipTests \
    && mv target/$(./mvnw help:evaluate -Dexpression=project.artifactId -q -DforceStdout)-$(./mvnw help:evaluate -Dexpression=project.version -q -DforceStdout).jar app.jar

# -----------------------------
# Stage 2: Development Image
# -----------------------------
FROM eclipse-temurin:21-jre-jammy AS development
WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/app.jar ./app.jar

# Enable remote debugging
ENV JAVA_TOOL_OPTIONS="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000"

# Expose app and debugger ports
EXPOSE 8080
EXPOSE 8000

# Run the app in dev mode
CMD ["java", "-Dspring.profiles.active=development", "-jar", "app.jar"]
