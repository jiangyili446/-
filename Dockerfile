FROM openjdk:12
WORKDIR /app/
COPY lab2.java ./lab2.java
RUN javac lab2.java
