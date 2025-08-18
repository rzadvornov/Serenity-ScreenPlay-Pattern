FROM maven:3.9.4-eclipse-temurin-21

# 1. Create non-root user with home directory
RUN useradd -m -d /home/mavenuser -s /bin/bash mavenuser

# 2. Create /app as mavenuser before setting WORKDIR
RUN mkdir -p /app && chown -R mavenuser:mavenuser /app

# 3. Set working directory
WORKDIR /app

# 4. Switch to non-root user
USER mavenuser

# 5. Ensure Maven local repository exists
RUN mkdir -p /home/mavenuser/.m2

# 6. Copy pom.xml first and download dependencies
COPY --chown=mavenuser:mavenuser pom.xml .
RUN mvn dependency:go-offline -B

# 7. Copy source code
COPY --chown=mavenuser:mavenuser src ./src

# 8. Pre-create target directory to avoid resource-plugin errors
RUN mkdir -p /app/target/classes

# 9. Run tests (skip clean to avoid Serenity/Failsafe locking issues)
CMD ["mvn", "verify", "-Dmaven.clean.skip=true", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
