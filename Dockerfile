FROM openjdk:12
WORKDIR /app/
COPY lab5.java ./lab5.java
RUN javac lab5.java
