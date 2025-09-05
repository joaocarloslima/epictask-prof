# Etapa 1: Build da aplicação
FROM gradle:8.4.0-jdk17 AS build

WORKDIR /app

COPY . .

# Gera o JAR usando o plugin do Spring Boot
RUN gradle bootJar --no-daemon

# Etapa 2: Imagem final com JDK otimizado
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copia apenas o JAR gerado na etapa anterior
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

# Executa a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]