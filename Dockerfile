ARG MAVEN_IMAGE=maven:3.9.4-eclipse-temurin-21
ARG APP_DIR=/app
ARG BASE_URL=https://waarkoop-server.herokuapp.com/

FROM ${MAVEN_IMAGE}

WORKDIR ${APP_DIR}

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Run tests (clean is optional)
CMD ["mvn", "verify", "-Drestapi.baseurl=${BASE_URL}"]
