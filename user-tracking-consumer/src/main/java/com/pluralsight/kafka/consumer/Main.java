package com.pluralsight.kafka.consumer;


import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Properties;

import static java.util.Arrays.asList;

@Slf4j
public class Main {
    private static final String TOPIC = "user-tracking";
    public static void main(String[] args) {

        SuggestionEngine suggestionEngine = new SuggestionEngine();

        Properties props = new Properties();

        // Because now we make use of the broker deployed on a docker container
        // taken from the docker-compose.yml of
        // https://github.com/confluentinc/cp-all-in-one/tree/7.2.1-post/cp-all-in-one-community
        // props.put("bootstrap.servers", "localhost:9093,localhost:9094");
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", TOPIC + "-consumer");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        try(KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {

            consumer.subscribe(asList("user-tracking"));

            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record : records) {
                    suggestionEngine.processSuggestions(record.key(), record.value());
                }
            }
        } catch (Exception e) {
            log.error(String.format("An exception was raised whilst trying to consume from %s", TOPIC), e);
            throw new RuntimeException(e);
        }
    }
}
