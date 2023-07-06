package com.fabulouslab.spark.e04_stream

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{current_date, from_json}
import org.apache.spark.sql.streaming.OutputMode
import org.apache.spark.sql.streaming.OutputMode.Complete
import org.apache.spark.sql.types.{LongType, StringType, StructField, StructType}


object E05_agg {

  def main(args: Array[String]) {
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{avg, max, expr}

object PageviewsAggregationApp {
  def main(args: Array[String]): Unit = {
    // Création de la session Spark
    val spark = SparkSession.builder()
      .appName("PageviewsAggregationApp")
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
      .selectExpr("CAST(value AS STRUCT<userId: STRING, pageId: STRING, timestamp: STRING>)")

    // Transformation des données pour calculer le temps moyen passé sur une page
    val avgTimeDF = pageviewsDF.withColumn("timestamp", expr("CAST(timestamp AS TIMESTAMP)"))
      .groupBy("pageId")
      .agg(avg(expr("UNIX_TIMESTAMP() - UNIX_TIMESTAMP(timestamp)")).alias("average_time"))

    // Transformation des données pour calculer le temps maximum passé sur une page
    val maxTimeDF = pageviewsDF.withColumn("timestamp", expr("CAST(timestamp AS TIMESTAMP)"))
      .groupBy("pageId")
      .agg(max(expr("UNIX_TIMESTAMP() - UNIX_TIMESTAMP(timestamp)")).alias("maximum_time"))

    // Transformation des données pour calculer à la fois le temps moyen et le temps maximum passé sur une page
    val avgMaxTimeDF = pageviewsDF.withColumn("timestamp", expr("CAST(timestamp AS TIMESTAMP)"))
      .groupBy("pageId")
      .agg(
        avg(expr("UNIX_TIMESTAMP() - UNIX_TIMESTAMP(timestamp)")).alias("average_time"),
        max(expr("UNIX_TIMESTAMP() - UNIX_TIMESTAMP(timestamp)")).alias("maximum_time")
      )

    // Affichage des résultats en continu dans la console
    val query1 = avgTimeDF.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    val query2 = maxTimeDF.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    val query3 = avgMaxTimeDF.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    // Démarrage de la lecture en continu
    query1.awaitTermination()
    query2.awaitTermination()
    query3.awaitTermination()
  }
}

  }

}
