FROM clojure:openjdk-11-tools-deps-slim-buster AS builder
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN ["clojure", "-X:depstar"]

FROM alpine:latest
RUN apk --no-cache add openjdk11-jre
COPY --from=builder /usr/src/app/bond.jar /usr/src/app/bond.jar
WORKDIR /usr/src/app
ENTRYPOINT ["java", "-jar", "bond.jar"]
