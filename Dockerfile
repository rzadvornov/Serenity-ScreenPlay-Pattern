FROM maven:3.9.4-eclipse-temurin-21

# Create user with home dir
RUN useradd -m -d /home/mavenuser -s /bin/bash mavenuser

WORKDIR /app

# Copy pom.xml and src with ownership
COPY --chown=mavenuser:mavenuser pom.xml .
COPY --chown=mavenuser:mavenuser src ./src

# Fix ownership of /app (so target/ will also be owned by mavenuser)
RUN chown -R mavenuser:mavenuser /app

# Switch to user
USER mavenuser

# Ensure Maven local repo exists
RUN mkdir -p /home/mavenuser/.m2

RUN mvn dependency:go-offline -B

CMD ["mvn", "clean", "verify", "-Drestapi.baseurl=https://waarkoop-server.herokuapp.com/"]
