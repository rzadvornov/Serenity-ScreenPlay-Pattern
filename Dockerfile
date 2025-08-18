FROM maven:3.9.4-eclipse-temurin-21

# Create user & group
RUN groupadd -r mavenuser && useradd -r -g mavenuser mavenuser

WORKDIR /app

# Copy pom.xml and src with proper ownership
COPY --chown=mavenuser:mavenuser pom.xml .
COPY --chown=mavenuser:mavenuser src ./src

# Switch to non-root user
USER mavenuser

# Download dependencies
RUN mvn dependency:go-offline -B

# Run tests
CMD ["mvn", "clean", "verify", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
