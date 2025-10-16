FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY src/ src/
RUN javac -d bin src/*.java
CMD ["java", "-cp", "bin", "ApiServer"]