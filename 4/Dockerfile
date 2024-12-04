FROM swift:latest

COPY . /app
WORKDIR /app
RUN swiftc -o main main.swift

CMD ["./main"]
