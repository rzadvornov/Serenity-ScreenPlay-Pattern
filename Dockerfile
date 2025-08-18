FROM maven:3.9.4-eclipse-temurin-21

# Create non-root user with home
RUN useradd -m -d /home/mavenuser -s /bin/bash mavenuser

# Set working directory
WORKDIR /app

# Give ownership of the whole /app directory to mavenuser
RUN chown -R mavenuser:mavenuser /app

# Switch to non-root user before running any Maven command
USER mavenuser

# Ensure Maven local repo exists
RUN mkdir -p /home/mavenuser/.m2

# Copy pom.xml (files already owned by mavenuser)
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Run tests (skip clean to avoid target deletion issues)
CMD ["mvn", "verify", "-Dmaven.clean.skip=true", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
