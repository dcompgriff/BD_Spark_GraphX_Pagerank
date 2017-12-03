#############################################
Assignment 3, Part B, App1, GraphX page 
rank versus Spark page rank.
#############################################
Developer: Daniel Griffin
Date: 11/27/17

#############################################
Project Description
#############################################
THis project deals with the development of a GraphX based 
implementation of PageRank, and that application's 
comparison to a PageRank implementation in Spark. The 
GraphX based implementation uses the Pregel API, and outputs 
PageRank results to the hadoop folder "/tmp/ranks.csv/". 
The GraphX PageRank implementation is run for 20 iterations. 
The Spark implementation of PageRank that was used to test 
against GraphX can be found at 
"/home/ubuntu/assignment3/partb/app1_spark". The spark 
version can be run with the command "./runApp1.sh" from 
that directory. The spark version of GraphX outputs 
results to the hadoop folder "/tmp/spark_ranks.csv/". The 
page rank analysis was run on the graph file 
"/assignment3/soc-LiveJournal1.txt" in the hadoop 
filesystem. This file can also be found at the link
"http://snap.stanford.edu/data/soc-LiveJournal1.html".


#############################################
Assumptions made by the scripts
#############################################
1) The input graph file is at "/assignment3/soc-LiveJournal1.txt"

2) The output will be placed at "/tmp/ranks.csv/".

3) The GraphX implementation will run for only 20 iterations.

#############################################
Running the scripts
#############################################
1) ./runApp1.sh
Running this script will compile the .scala application into 
a jar using the scala build tool sbt (which requires the 
sbt.sh script found in this local directory) to create 
a jar. It the submits the .jar application to the 
spark cluster to run.

2) /runApp1WithStats.sh
Running this script will compile the .scala application into 
a jar using the scala build tool sbt (which requires the 
sbt.sh script found in this local directory) to create 
a jar. It the submits the .jar application to the 
spark cluster to run. This script also outputs the cluster 
information about disk and network usage to calculate 
application bandwidth usage.





















