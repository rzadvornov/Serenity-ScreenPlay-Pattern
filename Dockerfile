FROM maven:3.9.4-eclipse-temurin-21

# 1. Create non-root user with home directory
RUN useradd -m -d /home/mavenuser -s /bin/bash mavenuser

# 2. Create /app as mavenuser and set as working directory
RUN mkdir -p /app && chown -R mavenuser:mavenuser /app
WORKDIR /app

# 3. Switch to non-root user before running Maven
USER mavenuser

# 4. Ensure Maven local repository exists
RUN mkdir -p /home/mavenuser/.m2

# 5. Copy pom.xml first and download dependencies
COPY --chown=mavenuser:mavenuser pom.xml .
RUN mvn dependency:go-offline -B

# 6. Copy source code
COPY --chown=mavenuser:mavenuser src ./src

# 7. Ensure target directory exists and writable
RUN mkdir -p /app/target/classes

# 8. Run tests (skip clean to avoid target deletion issues)
CMD ["mvn", "verify", "-Dmaven.clean.skip=true", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
