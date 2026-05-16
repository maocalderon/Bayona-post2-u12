# ── Etapa 1: compilación con Maven + JDK ─────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /app
# Copiar pom.xml primero para aprovechar caché de capas de Docker
COPY pom.xml .
RUN mvn dependency:go-offline -q 2>/dev/null || true
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Etapa 2: imagen de producción (solo JRE, más pequeña y segura) ────
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# Usuario no root por buenas prácticas de seguridad en contenedores
RUN addgroup -S spring && adduser -S spring -G spring
USER spring
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
