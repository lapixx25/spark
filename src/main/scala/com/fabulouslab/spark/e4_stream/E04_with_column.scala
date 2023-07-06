package com.fabulouslab.spark.e04_stream

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{current_date, from_json}
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}

object E04_with_column {

  def main(args: Array[String]) {

   import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, current_timestamp, expr, unix_timestamp}

object PageviewsEnrichmentApp {
  def main(args: Array[String]): Unit = {
    // Création de la session Spark
    val spark = SparkSession.builder()
      .appName("PageviewsEnrichmentApp")
      .getOrCreate()

    // Lecture du stream de données à partir du topic Kafka
    val pageviewsStream = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092")
      .option("subscribe", "pageviews")
      .load()

    // Conversion des données de la colonne "value" en chaîne de caractères
    val pageviewsDF = pageviewsStream
      .selectExpr("CAST(value AS STRING)")
      .select("value")

    // Transformation des données en ajoutant les colonnes de date et de temps passé sur la page
    val enrichedDF = pageviewsDF.withColumn("processing_date", current_timestamp())
      .withColumn("time_spent_seconds", expr("unix_timestamp(processing_date) - unix_timestamp(value.timestamp)"))

    // Affichage du DataFrame enrichi en continu dans la console
    val query = enrichedDF.writeStream
      .outputMode("append")
      .format("console")
      .start()

    // Démarrage de la lecture en continu
    query.awaitTermination()
  }
}



  }

}
