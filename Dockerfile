FROM openjdk:12
WORKDIR /app/
COPY lab8.java ./lab8.java
RUN javac lab8.java
