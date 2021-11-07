FROM openjdk:12
WORKDIR /app/
COPY lab1.java ./lab1.java
RUN javac lab1.java
