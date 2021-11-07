FROM openjdk:12
WORKDIR /app/
COPY lab1.java ./program.java
RUN javac program.java
