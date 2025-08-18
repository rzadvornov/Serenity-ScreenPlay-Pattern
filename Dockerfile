FROM maven:3.9.4-eclipse-temurin-21

WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Run tests (clean is optional)
CMD ["mvn", "verify", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
