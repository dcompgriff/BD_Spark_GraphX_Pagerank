import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession


object PageRankGraphX {
  def main(args: Array[String]) {
    //val graphFile = "/assignment3/test-soc-LiveJournal1.txt" // Should be some file on your system
    val graphFile = "/assignment3/soc-LiveJournal1.txt" // Should be some file on your system
    val spark = SparkSession.builder.appName("PageRankGraphX Application")
    	.config("spark.driver.memory","4g")
	.config("spark.executor.memory","8g")
	.config("spark.executor.cores","4")
	.config("spark.driver.cores","4")
	.config("spark.task.cpus", "2")
	.config("spark.graphx.pregel.checkpointInterval", "2")
    	.getOrCreate()
   
   spark.sparkContext.setCheckpointDir("/tmp/checkpoint")


    // Read and filter comments from graph file.
//    val unfilteredGraphData = spark.read.textFile(graphFile).cache()
//    val graphData = unfilteredGraphData.filter(line => !line.contains("#"))
    // Build graph verts and edges.
//    val uniqueNodeIds = graphData.flatmap(line => line.split("\\W+")).distinct()
//    val nodes: RDD[(VertexId, Double)] = uniqueNodeIds.map(id => (id, 0))
//    val edges: RDD[Edge[Double]] = graphData.map(line => (line.split("\\W+")(0), line.split("\\W+")(1), 0))

    val graphData = GraphLoader.edgeListFile(spark.sparkContext, graphFile)
    val damping = 0.15

    /*
     Create the initial graph. Vertex values will be the current node's rank, and 
     edge values will be one over the link factor, or the degree.
    */

    // Construct an output degree vertex graph.
    val outDeg: VertexRDD[Int] = graphData.outDegrees
    val degreeGraph = graphData.outerJoinVertices(outDeg) { (id, attr, outDegProp) => outDegProp match {
     	case Some(outDeg) => outDeg
	case None => 0}
    }

    // Create graph with each vertex mapped to it's out degree.
    val inputGraph: Graph[Int, Int] = degreeGraph.outerJoinVertices(degreeGraph.outDegrees)((vid, _, degOpt) => degOpt.getOrElse(0))
    // Create graph with edges mapped to one over the source vertex degree.
    val initialPRGraph: Graph[Double, Double] = inputGraph.mapTriplets(triplet => 1.0 / triplet.srcAttr).mapVertices((id, _) => 1.0).cache()

    /*
    Create the functions necessary for the Pregel API.
    */
    // The initial message received by all vertices in PageRank
    val initialRankSum = 0.0

    // Define the message aggregator for aggregating messages.
    def messageAggregator(rank1: Double, rank2: Double): Double = rank1 + rank2

    // Define the vertex function for setting the node rank.
    def vertexFunction(vid: VertexId, vattr: Double, sum: Double): Double = .15 + (.85*sum)
    
    // Define the message function for sending messages.
    def sendMessage(edgeTup: EdgeTriplet[Double, Double]) = Iterator((edgeTup.dstId, edgeTup.srcAttr * edgeTup.attr))

    // Execute pregel for a fixed number of iterations.
    val finalPRGraph = Pregel(initialPRGraph, initialRankSum, 20, activeDirection = EdgeDirection.Out)(vertexFunction, sendMessage, messageAggregator)

    // Get the final page ranks as an RDD.
    val finalRanksRDD = finalPRGraph.vertices.map(vertexTup => vertexTup.toString)
    finalRanksRDD.saveAsTextFile("/tmp/ranks.csv")

    spark.stop()
  }
}


