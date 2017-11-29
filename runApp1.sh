#!/bin/bash

source ~/run.sh
hadoop fs -rmr /tmp/ranks.csv
./sbt.sh package
spark-submit --class "PageRankGraphX" --master spark://10.254.0.177:7077 ./target/scala-2.11/pagerankgraphx_2.11-1.0.jar
