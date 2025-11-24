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
FROM eclipse-temurin:21-jdk-alpine

ARG APP_DIR=/app
ARG BASE_URL=https://waarkoop-server.herokuapp.com/

ENV APP_DIR=$APP_DIR
ENV BASE_URL=$BASE_URL

ENV HOME=/home/appuser

# Install packages
RUN apk update && apk add shadow maven \
    && rm -rf /var/cache/apk/*

RUN groupadd -r appgroup && useradd -r -g appgroup -d $HOME appuser

# The appuser must own their home and their .m2 folder to write the repository.
RUN mkdir -p $HOME/.m2 \
    && chown -R appuser:appgroup $HOME

WORKDIR $APP_DIR

# 1. Copy compiled project files
COPY --from=builder $APP_DIR/pom.xml .
COPY --from=builder $APP_DIR/src ./src
COPY --from=builder $APP_DIR/target/classes ./target/classes
COPY --from=builder $APP_DIR/target/test-classes ./target/test-classes

# 2. Maven dependency cache (Optional but recommended for speed)
# If you want to use the cache, copy it to the new user's home and set ownership.
# If you skip this, Maven will re-download everything on the first run, but it will work.
COPY --from=builder /root/.m2 $HOME/.m2 
RUN chown -R appuser:appgroup $HOME/.m2

# Give the non-root user ownership of the application directory
RUN chown -R appuser:appgroup $APP_DIR

# Switch to the non-root user for the final execution
USER appuser

# Set the final command to execute Serenity tests and aggregate reports
CMD ["/bin/sh", "-c", "mvn verify -Drestapi.baseurl=${BASE_URL} -Dmaven.test.failure.ignore=true; mvn serenity:aggregate"]
