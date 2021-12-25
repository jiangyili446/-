FROM openjdk:12
WORKDIR /app/
COPY lab7.java ./lab7.java
RUN javac lab7.java
