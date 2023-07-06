package com.fabulouslab.spark.e04_stream

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.avro.to_avro
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.sql.functions.from_json



object E01_hello_kafka {

  def main(args: Array[String]) {

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class PageviewsConsumer {
    public static void main(String[] args) {
        // Configuration du consommateur Kafka
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "pageviews-consumer");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        // Création du consommateur Kafka
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // S'abonner au topic "pageviews"
        consumer.subscribe(Collections.singletonList("pageviews"));

        // Boucle de consommation des messages
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("Clé: " + record.key() + ", Valeur: " + record.value());
            }
        }
    }
}

  }

}
