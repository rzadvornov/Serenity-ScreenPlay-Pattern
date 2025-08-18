FROM maven:3.9.4-eclipse-temurin-21

# 1. Create non-root user with home directory
RUN useradd -m -d /home/mavenuser -s /bin/bash mavenuser

# 2. Set working directory
WORKDIR /app

# 3. Ensure /app is owned by mavenuser
RUN chown -R mavenuser:mavenuser /app

# 4. Switch to non-root user
USER mavenuser

# 5. Ensure Maven local repository exists
RUN mkdir -p /home/mavenuser/.m2

# 6. Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# 7. Copy source code
COPY src ./src

# 8. Run tests (skip clean to avoid target deletion issues)
CMD ["mvn", "verify", "-Dmaven.clean.skip=true", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
