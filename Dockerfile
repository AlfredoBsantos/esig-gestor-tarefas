# Etapa 1: Usar uma imagem base oficial do Maven para compilar nosso projeto
FROM maven:3.8-jdk-8 AS build

# Copia o código-fonte do nosso projeto para dentro do container
COPY src /home/app/src
COPY pom.xml /home/app

# Executa o comando do Maven para compilar e gerar o arquivo .war
# O -DskipTests pula os testes unitários para acelerar o deploy
RUN mvn -f /home/app/pom.xml clean package -DskipTests

# Etapa 2: Usar uma imagem leve do Tomcat 9 com Java 8 para rodar a aplicação
FROM tomcat:9.0-jdk8-temurin

# Remove a aplicação padrão do Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copia o arquivo .war que foi gerado na Etapa 1 para a pasta webapps do Tomcat.
# Renomeamos para ROOT.war para que a aplicação rode na URL principal (ex: seudominio.onrender.com/)
COPY --from=build /home/app/target/esig-gestor-tarefas-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Expõe a porta 8080, que é a porta padrão do Tomcat
EXPOSE 8080

# Comando para iniciar o Tomcat quando o container for executado
CMD ["catalina.sh", "run"]