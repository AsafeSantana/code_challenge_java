# Usando uma imagem do Maven com OpenJDK 17 para o estágio de build
FROM maven:3.8.4-openjdk-17 AS build

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando o arquivo pom.xml e baixando as dependências
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Usando uma imagem leve do OpenJDK para a aplicação final
FROM openjdk:17-jdk-slim

# Definindo o diretório de trabalho
WORKDIR /app

# Copiando o jar gerado no estágio de build
COPY --from=build /app/target/*.jar app.jar

# Definindo o ponto de entrada
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
