# Use Maven with OpenJDK 21
FROM maven:3.9.4-eclipse-temurin-21

# Set working directory
WORKDIR /app

# Copy pom.xml first for better Docker layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Set default command to run tests
CMD ["mvn", "clean", "verify", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]