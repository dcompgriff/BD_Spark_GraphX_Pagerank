import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession


object PageRankGraphX {
  def main(args: Array[String]) {
    val graphFile = "/assignment3/soc-LiveJournal1.txt" // Should be some file on your system
    val spark = SparkSession.builder.appName("PageRankGraphX Application")
    	.config("spark.driver.memory","4g")
	.config("spark.executor.memory","4g")
	.config("spark.executor.cores","4")
	.config("spark.driver.cores","4")
	.config("spark.task.cpus", "1")
    	.getOrCreate()
   

    // Read and filter comments from graph file.
//    val unfilteredGraphData = spark.read.textFile(graphFile).cache()
//    val graphData = unfilteredGraphData.filter(line => !line.contains("#"))
    // Build graph verts and edges.
//    val uniqueNodeIds = graphData.flatmap(line => line.split("\\W+")).distinct()
//    val nodes: RDD[(VertexId, Double)] = uniqueNodeIds.map(id => (id, 0))
//    val edges: RDD[Edge[Double]] = graphData.map(line => (line.split("\\W+")(0), line.split("\\W+")(1), 0))

     val graphData = GraphLoader.edgeListFile(spark.sparkContext, graphFile)

    /*
     For i in 0 to 19 (20 iterations)
     	 Aggregate messages and calculate value at each vertex.
    */

    println("Loaded graphData object!")



    



//    spark.stop()
  }
}


