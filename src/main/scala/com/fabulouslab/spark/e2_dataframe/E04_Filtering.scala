package com.fabulouslab.spark.e2_dataframe

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object E04_Filtering {

  def main(args: Array[String]) {


    /**
      * En utilisant filter :
      *   - Comptez le nombre de vidéos qui n'ont aucun commentaire
      *   - Comptez le nombre de vidéos qui ont plus que 10000 commentaires
      *   - Il existe plusieurs manières d'écrire un filtre,  essayez de réécrire
      *     le filtre précédent d'une autre façon pour varier le plaisir !
      *   - C'est quoi le différence entre la fonction where et filter ?
      *
      * */

    val sparkSession = SparkSession.builder
      .master("local[1]")
      .appName("exo-3")
      .getOrCreate()

    import sparkSession.implicits._
    sparkSession.close()

  }

}
