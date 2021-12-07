FROM openjdk:12
WORKDIR /app/
COPY lab6.java ./lab6.java
RUN javac lab6.java
