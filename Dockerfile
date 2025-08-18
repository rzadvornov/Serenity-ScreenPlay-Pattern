# Build arguments
ARG MAVEN_IMAGE=maven:3.9.4-eclipse-temurin-21
ARG APP_DIR=/app
ARG BASE_URL=https://waarkoop-server.herokuapp.com/

FROM ${MAVEN_IMAGE}

# Set working directory
WORKDIR ${APP_DIR}

# Ensure target directory exists and writable
RUN mkdir -p ${APP_DIR}/target/classes && chmod -R 777 ${APP_DIR}/target

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Set BASE_URL as ENV
ENV BASE_URL=${BASE_URL}
ENV PROJECT_DIR=${APP_DIR}

# Run tests with shell form (allows ENV expansion)
CMD mvn verify \
    -Drestapi.baseurl=${BASE_URL} \
    -Dserenity.project.dir=${PROJECT_DIR}
