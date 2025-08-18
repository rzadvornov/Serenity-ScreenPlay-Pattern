FROM maven:3.9.4-eclipse-temurin-21

RUN useradd -m -d /home/mavenuser -s /bin/bash mavenuser

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src

RUN chown -R mavenuser:mavenuser /app
USER mavenuser

RUN mkdir -p /home/mavenuser/.m2

CMD ["sh", "-c", "rm -rf /app/target && mvn -Dmaven.clean.skip=true verify -Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
