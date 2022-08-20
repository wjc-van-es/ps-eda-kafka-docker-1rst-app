# ps-eda-kafka-docker-1rst-app

## Content
This repository contains example source code from the PluralSight course:

### [Designing Event-driven Applications Using Apache Kafka Ecosystem](https://app.pluralsight.com/library/courses/designing-event-driven-applications-apache-kafka-ecosystem/table-of-contents)

### by Bogdan Sucaciu

### Code example from Module 3: [Building Your First Apache Kafka Application](https://app.pluralsight.com/course-player?clipId=4dbf4867-a9b2-445b-b87e-7f51dbce8fb7#:~:text=3,Apache%20Kafka%20Application)
I enjoyed this course very much and I would recommend it to anyone who needs an introduction in event-driven design and would like
to use Apache Kafka software to implement it.

## Purpose
In the course the example was presented running with locally installed kafka, zookeeper & the Confluent schema-registry software components.
This requires some work, which may be instructive if you want to learn about some basic principles of Kafka and how to configure it.
However, it is much more convenient to run all necessary software components in separate docker containers. Especially when you are
already familiar with Docker and Docker Compose technology.

### The software components
The example code, which is basically a producer writing to and a consumer reading from a single Kafka topic using plain 
text for both the key and value part of the messages being written to the topic. This example code is still build
and run as small Java applications executing their respective main methods on your local computer. Presumably with help
from your favorite IDE. (So this part isn´t deployed to docker containers yet).

The Kafka cluster the example code communicates with, however, is entirely deployed as docker containers:
- one container with a single Apache kafka broker, listening on port 2181,
- one container with a single Zookeeper instance, listening on port 9092

### Making good use of the Confluent Platform Community Edition components
To get this set up to work quickly, I created a [docker/docker-compose.yml](docker/docker-compose.yml) file based on the
one found in
[GitHub repo: confluentinc cp-all-in-one-community 7.2.1-post](https://github.com/confluentinc/cp-all-in-one/tree/7.2.1-post/cp-all-in-one-community).
- 7.2.1-post is the current default branch reflecting the latest versions of the Apache Kafka & Confluent technology
  stack at the date of writing (August 2022).
- cp-all-in-one-community refers to all components of Confluent platform technology stack that fall under the
  [confluent-community-license](https://www.confluent.io/confluent-community-license/). All source code under this licence
  may be accessed, modified and redistributed freely except for creating a SaaS that tries to compete with Confluent.
- To run the original cp-all-in-one-community docker compose offering and explore their code examples see:
  - [cp-all-in-one-community documentation](https://docs.confluent.io/platform/current/tutorials/build-your-own-demos.html?utm_source=github&utm_medium=demo&utm_campaign=ch.examples_type.community_content.cp-all-in-one#cp-all-in-one-community)
  - [CE Docker Quickstart documentation](https://docs.confluent.io/platform/current/quickstart/ce-docker-quickstart.html)
  - [Further code examples in various languages](https://docs.confluent.io/platform/current/tutorials/examples/clients/docs/clients-all-examples.html#clients-all-examples)

From this all-in-one [`docker-compose.yml`](https://github.com/confluentinc/cp-all-in-one/blob/7.2.1-post/cp-all-in-one-community/docker-compose.yml)
, which defines all the components that are a part of the Confluent platform community edition,
we only took the two services that are needed to make the example code work and copied them in our own Docker Compose yaml file.
So, under the hood, we are using Docker images, made available by Confluent (for which we are grateful).

## Changes made to the original example source code.
I made an effort to update all maven dependencies to the versions available now (August 2022).

## Usage
- Open a terminal in the project/repository root dir
    ```bash
    $ cd docker
    $ docker compose up -d
    $ docker compose ps
    ```
- When the last command shows you that all three services are up and running, you can proceed to create the
  `user-tracking` topic in the same terminal with
   ```bash
   $ ./create-topic.sh
   ```
- Build the example code with maven (from the project/repository root dir)
  ```bash
  $ mvn clean compile -e
  ```
- On the command line or within your IDE
  - Run the Main class of the user-tracker-consumer module [com.pluralsight.kafka.consumer.Main](user-tracking-consumer/src/main/java/com/pluralsight/kafka/consumer/Main.java).
    - This application will keep running until you stop its process with Ctrl+C
  - Run the Main class of the user-tracker-producer module [com.pluralsight.kafka.producer.Main](user-tracking-producer/src/main/java/com/pluralsight/kafka/producer/Main.java).
    - This application will exit after publishing ten events on the 'user-tracking' topic, but you may run it multiple
      times to see multiples of ten events being processed by the consumer. 