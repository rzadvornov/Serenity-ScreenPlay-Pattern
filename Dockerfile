# ==========================================
# Stage 1: Builder - Dependency Caching & Compilation
# ==========================================
FROM maven:3.9.6-eclipse-temurin-21 AS builder

ARG APP_DIR=/app
ARG BASE_URL=https://waarkoop-server.herokuapp.com/

WORKDIR $APP_DIR

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn test-compile -B

# ==========================================
# Stage 2: Runtime - Test Execution (Secured)
# ==========================================
FROM temurin:21-jdk-slim

ARG APP_DIR=/app
ARG BASE_URL=https://waarkoop-server.herokuapp.com/

ENV APP_DIR=$APP_DIR
ENV BASE_URL=$BASE_URL

# Create a dedicated, non-privileged user and group
RUN groupadd -r appgroup && useradd -r -g appgroup appuser

WORKDIR $APP_DIR

# 1. Copy compiled project files
COPY --from=builder $APP_DIR/pom.xml .
COPY --from=builder $APP_DIR/src ./src
COPY --from=builder $APP_DIR/target/classes ./target/classes
COPY --from=builder $APP_DIR/target/test-classes ./target/test-classes

# 2. Copy the cached Maven dependencies
# Note: The /root/.m2 folder is outside $APP_DIR, so ownership change is not required for it.
COPY --from=builder /root/.m2 /root/.m2

# Give the non-root user ownership of the application directory
RUN chown -R appuser:appgroup $APP_DIR
# Switch to the non-root user for the final execution
USER appuser

# Set the final command to execute Serenity tests and aggregate reports
CMD ["/bin/bash", "-c", "mvn verify -Drestapi.baseurl=${BASE_URL} -Dmaven.test.failure.ignore=true; mvn serenity:aggregate"]
