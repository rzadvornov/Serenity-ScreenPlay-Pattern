FROM maven:3.9.4-eclipse-temurin-21

# 1. Create a non-root user
RUN useradd -m -d /home/mavenuser -s /bin/bash mavenuser

# 2. Create /app and give ownership to mavenuser
RUN mkdir -p /app && chown -R mavenuser:mavenuser /app

# 3. Switch to non-root user BEFORE any Maven commands
USER mavenuser

# 4. Set working directory
WORKDIR /app

# 5. Ensure Maven local repository exists
RUN mkdir -p /home/mavenuser/.m2

# 6. Copy pom.xml and download dependencies as mavenuser
COPY --chown=mavenuser:mavenuser pom.xml .
RUN mvn dependency:go-offline -B

# 7. Copy source code
COPY --chown=mavenuser:mavenuser src ./src

# 8. Ensure target directory exists
RUN mkdir -p /app/target/classes

# 9. Run tests (skip clean to avoid locking issues)
CMD ["mvn", "verify", "-Dmaven.clean.skip=true", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
