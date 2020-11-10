# Mutiny Supes Demo!

This repository is a demo of the [Mutiny!](https://smallrye.io/smallrye-mutiny/) integration with Quarkus.

Associated slides are available on https://bit.ly/quarkus-reactive-mutiny.

## Architecture

The demo is composed by:

* a MongoDB database pre-populated with supes
* a Kafka broker
* a Supes service retrieving heroes and villains from the MongoDB
* a Fight service using the Supes service and organize battles between heroes and villains. The outcome is available from an HTTP endpoint and sent to Kafka
* a Stats service collecting fight results from Kafka

## Run 

You need four terminals to run the application and at least one other terminal for the HTTP interactions.

**Infrastructure**

```shell script
cd infrastructure
docker-compose up
```

**Supes Service**

```shell script
cd supes-service
mvn quarkus:dev
```

HTTP API exposed on port 8080:

- http :8080/supes -> regular hello
- http :8080/supes/greetings -> Uni response
- http :8080/supes/hero -> random hero
- http :8080/supes/villain -> random villain

**Fight Service**

```shell script
cd fight-service
mvn quarkus:dev
```

HTTP API exposed on port 8081:

- http :8081/fight -> trigger a fight
- http :8081/stream --stream -> trigger a fight ever second, response as Server-Sent Events
- in a browser open http://localhost:8081 -> the addictive UI

**Stats Service**

```shell script
cd stats-service
mvn quarkus:dev
```

HTTP API exposed on port 8082:

- http :8082/stats --stream -> The current outcome (heroes vs. villains) as Server-Sent Events

