import org.apache.spark._
//import org.apache.spark.graphx._
//import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession


object PageRankGraphX {
  def main(args: Array[String]) {


    val logFile = "/wcount_input.txt" // Should be some file on your system
    val spark = SparkSession.builder.appName("PageRankGraphX Application").getOrCreate()

    val logData = spark.read.textFile(logFile).cache()
    val numAs = logData.filter(line => line.contains("a")).count()
    val numBs = logData.filter(line => line.contains("b")).count()
    println(s"Lines with a: $numAs, Lines with b: $numBs")
    spark.stop()
  }
}


