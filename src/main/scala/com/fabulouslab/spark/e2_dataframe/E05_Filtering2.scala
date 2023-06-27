package com.fabulouslab.spark.e2_dataframe

import org.apache.spark.sql.SparkSession

object E05_Filtering2 {

  def main(args: Array[String]) {

    /**
      * Maintenant, on va essayer de faire des filtres plus compliqués :
      *   - trouvez la liste des vidéos dans le channel MTV et le titre contient "Hand In Hand"
      *   - trouvez le nombre de vidéos avec un id catergory 28 ou aucun commentaire ?
      * */

    val sparkSession = SparkSession.builder
      .master("local[1]")
      .appName("exo-5")
      .getOrCreate()

    import sparkSession.implicits._


    sparkSession.close()

  }

}
