FROM openjdk:12
WORKDIR /app/
COPY lab3.java ./lab3.java
RUN javac lab3.java
