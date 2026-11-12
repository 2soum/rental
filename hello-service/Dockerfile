# --- build ---
FROM gradle:8.14-jdk17 AS builder
WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon > /dev/null 2>&1 || true

COPY src ./src
RUN gradle bootJar --no-daemon -x test

# --- run ---
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

RUN apt-get update && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/* \
    && useradd --system --no-create-home hello

COPY --from=builder --chown=hello:hello /app/build/libs/*.jar app.jar

USER hello
# le service ecoute sur 8081 (application.properties)
EXPOSE 8081

HEALTHCHECK --interval=30s --timeout=3s --start-period=20s \
  CMD curl -fs http://localhost:8081/hello || exit 1

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
