FROM openjdk:12
WORKDIR /app/
COPY lab4.java ./lab4.java
RUN javac lab4.java
