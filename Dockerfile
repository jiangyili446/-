FROM gcc:10
WORKDIR /app/
COPY test.cpp ./my-program.c
RUN gcc my-program.c -o program
RUN chmod +x program
