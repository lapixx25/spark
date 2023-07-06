package com.fabulouslab.spark.e04_stream

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.from_json
import org.apache.spark.sql.types.{BooleanType, LongType, StringType, StructField, StructType}


object E02_json {

  def main(args: Array[String]) {

object PageviewsStreamingApp {
  def main(args: Array[String]): Unit = {
    // Création de la session Spark
    val spark = SparkSession.builder()
      .appName("PageviewsStreamingApp")
      .getOrCreate()

    // Définition du schéma de la structure (Struct)
    val schema = StructType(Seq(
      StructField("userId", StringType),
      StructField("pageId", StringType),
      StructField("timestamp", StringType)
    ))

    // Configuration du streaming Kafka
    val kafkaParams = Map(
      "kafka.bootstrap.servers" -> "localhost:9092",
      "subscribe" -> "pageviews",
      "startingOffsets" -> "latest"
    )

    // Lecture des données du topic Kafka
    val kafkaStream = spark.readStream
      .format("kafka")
      .options(kafkaParams)
      .load()

    // Extraction de la valeur (payload) du message Kafka
    val valueStream = kafkaStream
      .selectExpr("CAST(value AS STRING)")
      .as[String]

    // Conversion de la valeur JSON en DataFrame avec le schéma défini
    val pageviewsDF = valueStream
      .select(from_json(col("value"), schema).as("data"))
      .select("data.*")

    // Affichage du DataFrame en continu dans la console
    val query: StreamingQuery = pageviewsDF.writeStream
      .outputMode(OutputMode.Append())
      .format("console")
      .start()

    // Démarrage de la lecture en continu
    query.awaitTermination()
  }
}

  }

}
