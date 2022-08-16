# READ ME
## PluralSight course: [Designing Event-driven Applications Using Apache Kafka Ecosystem](https://app.pluralsight.com/library/courses/designing-event-driven-applications-apache-kafka-ecosystem/table-of-contents)
### by Bogdan Sucaciu
## Module 02: [Building Your First Apache Kafka Application](https://app.pluralsight.com/course-player?clipId=4dbf4867-a9b2-445b-b87e-7f51dbce8fb7#:~:text=3,Apache%20Kafka%20Application)
### Purpose
This repository contains the source code of the example used in this module. The example was presented running with locally
installed kafka & zookeeper software components.

We would like to test this example with Docker and Zookeeper running inside Docker containers.
To get this set up to work quickly, we created a docker compose yaml file based on the one found in
[GitHub repo: confluentinc cp-all-in-one-community 7.2.1-post](https://github.com/confluentinc/cp-all-in-one/tree/7.2.1-post/cp-all-in-one-community).
- 7.2.1-post is the current default branch reflecting the latest versions of the Apache Kafka & Confluent technology stack at the date of writing (August 2022).
- cp-all-in-one-community refers to all parts of the Apache Kafka & Confluent technology stack that may be used freely (under licence ...).

From the docker-compose.yml, which contains all the services available to define a separate container each, we will compile our own
docker-compose.yml containing only the services / containers we need to get this example working.

