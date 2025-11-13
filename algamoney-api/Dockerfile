# Etapa 1: build
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copia os arquivos essenciais primeiro
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./

# Dá permissão de execução
RUN chmod +x mvnw

# Baixa dependências para cache
RUN ./mvnw dependency:go-offline

# Copia o restante do código
COPY . .

# ⚠️ Dá permissão de novo, pois o COPY sobrescreve o mvnw
RUN chmod +x mvnw

# Compila a aplicação
RUN ./mvnw clean package -DskipTests

# Etapa 2: runtime
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
