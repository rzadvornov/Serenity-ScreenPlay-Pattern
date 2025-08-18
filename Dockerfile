ARG MAVEN_IMAGE=maven:3.9.4-eclipse-temurin-21
ARG APP_DIR=/app
ARG BASE_URL=https://waarkoop-server.herokuapp.com/

# 2. Use Maven image
FROM ${MAVEN_IMAGE}

# 3. Set working directory
WORKDIR ${APP_DIR}

# 4. Ensure target directory exists and is writable
RUN mkdir -p ${APP_DIR}/target/classes && chmod -R 777 ${APP_DIR}/target

# 5. Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 6. Copy source code
COPY src ./src

# 7. Set environment variable for BASE_URL
ENV BASE_URL=${BASE_URL}

# 8. Run tests (shell form so ENV is expanded)
CMD mvn verify -Drestapi.baseurl=${BASE_URL}
