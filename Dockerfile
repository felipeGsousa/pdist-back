# Etapa de construção
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copie o arquivo pom.xml e as dependências para o contexto de construção
COPY pom.xml ./
RUN mvn dependency:go-offline

# Copie o restante do código do aplicativo
COPY src ./src

# Compile o aplicativo Spring Boot
RUN mvn clean package -DskipTests

# Etapa de execução
FROM openjdk:17-jdk-slim
WORKDIR /app

# Copie o arquivo JAR do Spring Boot da etapa de construção
COPY target/*.jar springboot-app.jar

# Copie o arquivo JAR do serviço gRPC da raiz do projeto
COPY file-grpc-service-1.0-SNAPSHOT.jar grpc-service.jar

# Exponha as portas que os serviços irão escutar
EXPOSE 8082
EXPOSE 50051

# Comando para iniciar o aplicativo Spring Boot e o serviço gRPC
ENTRYPOINT ["sh", "-c", "java -jar springboot-app.jar & java -jar grpc-service.jar"]

# Se você decidir usar o NGINX, descomente as seguintes linhas:
# RUN apt-get update && apt-get install -y nginx
# Copiar configuração do NGINX
# COPY nginx.conf /etc/nginx/nginx.conf
# Expor a porta que o NGINX vai escutar
# EXPOSE 80
# Iniciar o NGINX
# CMD service nginx start && java -jar app.jar & java -jar /usr/local/lib/file-grpc-service.jar


# # Etapa de construção
# FROM maven:3.8.6-eclipse-temurin-17 AS build
# WORKDIR /app
#
# # Copie o arquivo pom.xml e as dependências para o contexto de construção
# COPY pom.xml .
# RUN mvn dependency:go-offline
#
# # Copie o restante do código do aplicativo
# COPY src ./src
#
# # Compile o aplicativo
# RUN mvn clean package -DskipTests
#
# # Etapa de execução
# FROM openjdk:17-jdk-slim
# WORKDIR /app
#
# # Copie o arquivo JAR da etapa de construção
# COPY --from=build /app/target/*.jar app.jar
#
# # Exponha a porta que o serviço irá escutar
# EXPOSE 8080
#
# # Comando para iniciar o aplicativo
# ENTRYPOINT ["java", "-jar", "app.jar"]'