FROM maven:3.9.4-eclipse-temurin-21

# Create non-root user with home dir
RUN useradd -m -d /home/mavenuser -s /bin/bash mavenuser

WORKDIR /app

# Copy pom.xml and give ownership immediately
COPY --chown=mavenuser:mavenuser pom.xml .

# Switch to non-root user *before* running Maven
USER mavenuser

# Ensure local Maven repo exists
RUN mkdir -p /home/mavenuser/.m2

# Download dependencies (runs as mavenuser)
RUN mvn dependency:go-offline -B

# Copy source code with correct ownership
COPY --chown=mavenuser:mavenuser src ./src

# Run tests (no clean to avoid target deletion issues)
CMD ["mvn", "verify", "-Dmaven.clean.skip=true", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
