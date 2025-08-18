# Define build-time args with defaults
ARG MAVEN_IMAGE=maven:3.9.4-eclipse-temurin-21
ARG APP_DIR=/app
ARG BASE_URL=https://waarkoop-server.herokuapp.com/

FROM ${MAVEN_IMAGE}

# Persist args as environment variables
ENV APP_DIR=${APP_DIR}
ENV BASE_URL=${BASE_URL}

# Set working directory (absolute path)
WORKDIR ${APP_DIR}

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Run tests by default (JSON exec form for CMD)
CMD ["mvn", "verify", "-Drestapi.baseurl=${BASE_URL}"]
