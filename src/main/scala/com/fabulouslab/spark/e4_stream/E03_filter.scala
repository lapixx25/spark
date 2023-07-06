package com.fabulouslab.spark.e04_stream

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{current_date, from_json}
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}


object E03_filter {

  case class View(viewtime: Long, userid: String, pageid: String)
  def main(args: Array[String]) {


import java.util.Properties;

public class PageviewsStreamProcessor {
    public static void main(String[] args) {
        // Configuration du Kafka Streams
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "pageviews-stream-processor");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");

        // Création du flux builder Kafka Streams
        StreamsBuilder builder = new StreamsBuilder();

        // Flux original à partir du topic "pageviews"
        KStream<String, String> pageviewsStream = builder.stream("pageviews");

        // Construction d'un flux qui contient uniquement les événements sur la page 3
        KStream<String, String> page3Stream = pageviewsStream.filter((key, value) -> {
            // Ajoutez ici votre logique de filtrage pour les événements sur la page 3
            // Exemple : return getPageId(value).equals("3");
        });

        // Construction d'un flux qui contient uniquement les événements sur le user 3
        KStream<String, String> user3Stream = pageviewsStream.filter((key, value) -> {
             return getUserId(value).equals("3");
        });

        // Construction d'un flux qui contient uniquement les événements sur le user 3 avec un temps de visite supérieur à 2 secondes
        KStream<String, String> user3LongVisitStream = pageviewsStream.filter((key, value) -> {
             return getUserId(value).equals("3") && getVisitDuration(value) > 2;
        });

        // Réinjection des flux filtrés dans les topics Kafka correspondants
        page3Stream.to("page3-events", Produced.with(page3Stream.keySerde(), page3Stream.valueSerde()));
        user3Stream.to("user3-events", Produced.with(user3Stream.keySerde(), user3Stream.valueSerde()));
        user3LongVisitStream.to("user3-long-visit-events", Produced.with(user3LongVisitStream.keySerde(), user3LongVisitStream.valueSerde()));

        // Création et démarrage du Kafka Streams
        KafkaStreams streams = new KafkaStreams(builder.build(), props);
        streams.start();

        // Arrêt propre du Kafka Streams lors de l'arrêt de l'application
        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }


}


  }

}
