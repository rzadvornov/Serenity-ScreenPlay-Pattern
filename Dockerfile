# Build args (can be overridden in `docker build`)
ARG MAVEN_IMAGE=maven:3.9.4-eclipse-temurin-21

FROM ${MAVEN_IMAGE}

# Re-declare args after FROM (Docker scoping rule)
ARG APP_DIR=/app
ARG BASE_URL=https://waarkoop-server.herokuapp.com/

# Promote args to env so they are available at runtime
ENV APP_DIR=$APP_DIR
ENV BASE_URL=$BASE_URL

# Use ENV for workdir (safe absolute path)
WORKDIR $APP_DIR

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Default command (exec form)
CMD ["/bin/bash", "-c", "mvn verify -Drestapi.baseurl=${BASE_URL} -Dmaven.test.failure.ignore=true; mvn serenity:aggregate"]

