FROM maven:3.9.4-eclipse-temurin-21

# Set working directory
WORKDIR /app

# Use the pre-created maven user
USER maven

# Copy pom.xml first for better Docker layer caching
COPY --chown=maven:maven pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY --chown=maven:maven src ./src

# Set default command to run tests
CMD ["mvn", "clean", "verify", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
